package com.polije.sosrobahufactoryapp.ui.factory.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DashboardPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.LogoutPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.UserSessionPabrikUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class HomePabrikViewModel(
    val dashboardPabrikUseCase: DashboardPabrikUseCase,
    val userSessionUseCase: UserSessionPabrikUseCase,
    private val logoutPabrikUseCase: LogoutPabrikUseCase
) :
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

    val isLogged =
        userSessionUseCase.isLoggingIn().stateIn(viewModelScope, SharingStarted.Eagerly, true)


    fun getDashboardPabrik() {
        viewModelScope.launch {
            _state.value = HomePabrikState.Loading
            val token = userSessionUseCase.invoke().first().token
            if (token == null) {
                _state.value =
                    HomePabrikState.Failure(HttpErrorCode.UNAUTHORIZED, "Token Tidak Berlaku")
                return@launch
            } else {

                try {
                    val response = dashboardPabrikUseCase.invoke()
                    when (response) {
                        is DataResult.Error -> {
                            _state.value =
                                HomePabrikState.Failure(
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
                            HomePabrikState.Success(
                                dashboardPabrik = response.data,
                                pendapatanBulanan = response.data.pesananPerbulan
                            )
                    }
                } catch (e: Exception) {
                    _state.value =
                        HomePabrikState.Failure(
                            errorCode = HttpErrorCode.UNKNOWN,
                            errorMessage = "Error: ${e.message}",
                        )

                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutPabrikUseCase.invoke()
        }
    }
}


