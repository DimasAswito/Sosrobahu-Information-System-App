package com.polije.sosrobahufactoryapp.ui.distributor.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.LoginDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.UserSessionDistributorUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DistributorLoginViewModel(
    val loginDistributorUseCase: LoginDistributorUseCase,
    val userSessionDistributorUseCase: UserSessionDistributorUseCase
) :
    ViewModel() {
    private var _distributorLoginState = MutableStateFlow(DistributorLoginState())

    private val _loginState = MutableStateFlow<LoginState>(
        LoginState.Idle
    )
    val loginState: StateFlow<LoginState> get() = _loginState.asStateFlow()

    val isValid = _distributorLoginState.map { state ->
        state.username.isNotBlank() && state.password.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onUsernameChanged(username: String) =
        _distributorLoginState.update { state -> state.copy(username = username) }

    fun onPasswordChanged(password: String) =
        _distributorLoginState.update { state -> state.copy(password = password) }

    val isAlreadyLoggedIn: StateFlow<Boolean> =
        userSessionDistributorUseCase.invoke()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = false
            )

    fun login() {
        viewModelScope.launch {
            viewModelScope.launch {
                _loginState.update { LoginState.Loading }
                val data = loginDistributorUseCase.invoke(
                    _distributorLoginState.value.username,
                    _distributorLoginState.value.password
                )
                when (data) {
                    is DataResult.Error -> {
                        _loginState.update {
                            LoginState.Error(
                                when (data.error) {
                                    HttpErrorCode.BAD_REQUEST -> "Permintaan tidak valid. Periksa kembali data yang dikirimkan."
                                    HttpErrorCode.UNAUTHORIZED -> "Login gagal. Username atau password salah."
                                    HttpErrorCode.FORBIDDEN -> "Akses ditolak. Anda tidak memiliki izin untuk mengakses."
                                    HttpErrorCode.NOT_FOUND -> "Server tidak ditemukan. Coba lagi nanti."
                                    HttpErrorCode.TIMEOUT -> "Permintaan melebihi batas waktu. Periksa koneksi internet Anda."
                                    HttpErrorCode.INTERNAL_SERVER_ERROR -> "Terjadi kesalahan pada server. Silakan coba beberapa saat lagi."
                                    HttpErrorCode.UNKNOWN -> "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi."
                                }
                            )
                        }
                    }

                    is DataResult.Success -> {
                        _loginState.update {
                            LoginState.Success(
                                true
                            )
                        }
                    }
                }
            }
        }


    }
}

