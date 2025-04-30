package com.polije.sosrobahufactoryapp.ui.distributor.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.DasbhoardDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.LogoutDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.UserSessionDistributorUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class HomeDistributorViewModel(
    userSessionDistributorUseCase: UserSessionDistributorUseCase,
    private val logoutDistributorUseCase: LogoutDistributorUseCase,
    private val dashboardDistributorUseCase: DasbhoardDistributorUseCase
) :
    ViewModel() {

    private val _state = MutableStateFlow<HomeDistributorState>(HomeDistributorState.Initial)
    val state = _state
        .onStart {
            getDashboardPabrik()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = HomeDistributorState.Initial
        )

    val isLogged =
        userSessionDistributorUseCase.isLoggingIn()
            .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun logout() {
        viewModelScope.launch {
            logoutDistributorUseCase.invoke()
        }
    }

    fun getDashboardPabrik() {
        viewModelScope.launch {
            _state.value = HomeDistributorState.Loading
            try {
                val response = dashboardDistributorUseCase.invoke()
                when (response) {
                    is DataResult.Error -> {
                        _state.value =
                            HomeDistributorState.Failure(
                                response.error,
                                errorMessage = when (response.error) {
                                    HttpErrorCode.BAD_REQUEST -> ""
                                    HttpErrorCode.UNAUTHORIZED -> ""
                                    HttpErrorCode.FORBIDDEN -> ""
                                    HttpErrorCode.NOT_FOUND -> ""
                                    HttpErrorCode.TIMEOUT -> ""
                                    HttpErrorCode.INTERNAL_SERVER_ERROR -> ""
                                    HttpErrorCode.UNKNOWN -> ""
                                },
                            )
                    }

                    is DataResult.Success -> _state.value =
                        HomeDistributorState.Success(
                            dashboardResponse = response.data,
                        )
                }
            } catch (e: Exception) {
                _state.value =
                    HomeDistributorState.Failure(
                        errorCode = HttpErrorCode.UNKNOWN,
                        errorMessage = "Error: ${e.message}",
                    )

            }
        }

    }

}