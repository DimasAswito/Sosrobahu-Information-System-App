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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
            started = SharingStarted.Lazily,
            initialValue = HomePabrikState.Initial
        )

    val isLogged =
        userSessionUseCase.invoke().stateIn(viewModelScope, SharingStarted.Eagerly, true)


    fun getDashboardPabrik() {
        viewModelScope.launch {
            _state.value = HomePabrikState.Loading

            try {
                val response = dashboardPabrikUseCase.invoke()
                when (response) {
                    is DataResult.Error -> {
                        _state.value =
                            HomePabrikState.Failure(
                                response.error,
                                errorMessage = when (response.error) {
                                    HttpErrorCode.BAD_REQUEST -> "Permintaan tidak valid. Periksa kembali data yang dikirimkan."
                                    HttpErrorCode.UNAUTHORIZED -> "Login gagal. Username atau password salah."
                                    HttpErrorCode.FORBIDDEN -> "Akses ditolak. Anda tidak memiliki izin untuk mengakses."
                                    HttpErrorCode.NOT_FOUND -> "Server tidak ditemukan. Coba lagi nanti."
                                    HttpErrorCode.TIMEOUT -> "Permintaan melebihi batas waktu. Periksa koneksi internet Anda."
                                    HttpErrorCode.INTERNAL_SERVER_ERROR -> "Terjadi kesalahan pada server. Silakan coba beberapa saat lagi."
                                    HttpErrorCode.UNKNOWN -> "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi."
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

    fun logout() {
        viewModelScope.launch {
            logoutPabrikUseCase.invoke()
        }
    }
}


