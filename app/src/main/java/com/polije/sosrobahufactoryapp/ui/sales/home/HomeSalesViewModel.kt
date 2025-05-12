package com.polije.sosrobahufactoryapp.ui.sales.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.sales.DashboardSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.LogOutSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.LoginSalesUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.UserSessionSalesUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeSalesViewModel(
    private val homeSalesUseCase: DashboardSalesUseCase,
    userSessionSalesUseCase: UserSessionSalesUseCase,
    private val logoutSalesUseCase: LogOutSalesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<HomeSalesState>(HomeSalesState.Initial)
    val state = _state.onStart {
        getDashboard()
    }
        .stateIn(viewModelScope, SharingStarted.Eagerly, HomeSalesState.Initial)

    fun logout() {
        viewModelScope.launch {
            logoutSalesUseCase.invoke()
        }
    }

    private fun getDashboard() {
        viewModelScope.launch {
            _state.value = HomeSalesState.Loading
            try {
                val response = homeSalesUseCase.invoke()
                when (response) {
                    is DataResult.Error -> {
                        _state.value =
                            HomeSalesState.Failure(

                                errorMessage = when (response.error) {
                                    HttpErrorCode.BAD_REQUEST -> "Permintaan tidak valid. Periksa kembali listBarangAgen yang dikirimkan."
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
                        HomeSalesState.Success(
                            dashboardResponse = response.data,
                        )
                }
            } catch (e: Exception) {
                _state.value =
                    HomeSalesState.Failure(
                        errorMessage = "Error: ${e.message}",
                    )

            }
        }
    }


    val isLogged =
        userSessionSalesUseCase.invoke()
            .stateIn(viewModelScope, SharingStarted.Eagerly, true)
}