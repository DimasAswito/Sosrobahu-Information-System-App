package com.polije.sosrobahufactoryapp.ui.agen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.agen.DashboardAgenUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.agen.UserSessionAgenUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeAgenViewModel(
    userSessionAgenUseCase: UserSessionAgenUseCase,
    private val dashboardAgenUseCase: DashboardAgenUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HomeAgenState>(HomeAgenState.Initial)
    val state: StateFlow<HomeAgenState>
        get() = _state.asStateFlow().onStart {
            getDashboard()
        }
            .stateIn(viewModelScope, SharingStarted.Lazily, HomeAgenState.Initial)

    private fun getDashboard() {
        viewModelScope.launch {
            _state.value = HomeAgenState.Loading
            try {
                val response = dashboardAgenUseCase.invoke()
                when (response) {
                    is DataResult.Error -> {
                        _state.value =
                            HomeAgenState.Failure(

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
                        HomeAgenState.Success(
                            dashboardResponse = response.data,
                        )
                }
            } catch (e: Exception) {
                _state.value =
                    HomeAgenState.Failure(
                        errorMessage = "Error: ${e.message}",
                    )

            }
        }
    }


    val isLogged =
        userSessionAgenUseCase.isLoggingIn()
            .stateIn(viewModelScope, SharingStarted.Eagerly, true)
}