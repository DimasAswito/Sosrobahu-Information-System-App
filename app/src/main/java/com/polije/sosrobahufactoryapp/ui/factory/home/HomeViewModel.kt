package com.polije.sosrobahufactoryapp.ui.factory.home

import PesananPerBulan
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.DashboardUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.TokenUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeViewModel(val dashboardUseCase: DashboardUseCase,val tokenUseCase: TokenUseCase) : ViewModel() {

    private val _state = MutableStateFlow(DashboardPabrikState())
    val state: StateFlow<DashboardPabrikState> = _state.asStateFlow()

    init {
        getDashboardPabrik()
    }

    fun getDashboardPabrik() {

        viewModelScope.launch {

            val token = tokenUseCase.getToken()
            if (token == null) {
                _state.update { it.copy(errorMessage = "Token tidak ditemukan, silakan login ulang.") }
                return@launch
            } else {

                try {
                    val response = dashboardUseCase.invoke()
                    when (response){
                        is DataResult.Error -> _state.update { it.copy(errorMessage = response.error) }
                        is DataResult.Success -> _state.update { it.copy(dashboardPabrik = response.data, pendapatanBulanan = response.data.pesananPerbulan) }
                    }
                } catch (e: Exception) {
                    _state.update { it.copy(errorMessage = "Error: ${e.message}")}
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


