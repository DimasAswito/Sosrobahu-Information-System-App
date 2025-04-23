package com.polije.sosrobahufactoryapp.ui.factory.home

import PesananPerBulan
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DashboardUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.TokenUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.time.Duration.Companion.seconds

class HomePabrikViewModel(val dashboardUseCase: DashboardUseCase, val tokenUseCase: TokenUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<HomePabrikState>(HomePabrikState.Initial)
    val state = _state
        .onStart {
            getDashboardPabrik()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = HomePabrikState.Initial
        )


    fun getDashboardPabrik() {
        viewModelScope.launch {
            _state.value = HomePabrikState.Loading
            val token = tokenUseCase.getToken()
            if (token == null) {
                _state.value = HomePabrikState.Failure("Token Tidak Berlaku")
                return@launch
            } else {

                try {
                    val response = dashboardUseCase.invoke()
                    when (response) {
                        is DataResult.Error -> _state.value =
                            HomePabrikState.Failure(
                                errorMessage = response.error,
                            )


                        is DataResult.Success -> _state.value =
                            HomePabrikState.Success(
                                dashboardPabrik = response.data,
                                pendapatanBulanan = response.data.pesananPerbulan
                            )
                    }
                } catch (e: Exception) {
                    _state.value =
                        HomePabrikState.Failure(
                            errorMessage = "Error: ${e.message}",
                        )

                }
            }
        }
    }

    fun processPendapatanBulanan(pesananPerbulan: Map<String, PesananPerBulan>) {
        val monthlyRevenue = mutableMapOf<String, Float>()
        val allMonths = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH)

        for (i in 0..currentMonthIndex) {
            monthlyRevenue[allMonths[i]] = 0f
        }

        for ((date, data) in pesananPerbulan) {
            val monthIndex = date.substring(5, 7).toInt() - 1
            if (monthIndex <= currentMonthIndex) {
                val monthLabel = allMonths[monthIndex]
                monthlyRevenue[monthLabel] = data.totalOmset.toFloat()
            }
        }
    }
}


