package com.polije.sosrobahufactoryapp.ui.agen.login

//import com.polije.sosrobahufactoryapp.domain.usecase.agen.LoginAgenUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.agen.LoginAgenUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AgenLoginViewModel(val loginAgenUseCase: LoginAgenUseCase) :
    ViewModel() {
    private var _agenLoginState = MutableStateFlow(AgenLoginState())

    private val _loginState = MutableStateFlow<LoginState>(
        LoginState.Idle
    )
    val loginState: StateFlow<LoginState> get() = _loginState.asStateFlow()

    val isValid = _agenLoginState.map { state ->
        state.username.isNotBlank() && state.password.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onUsernameChanged(username: String) =
        _agenLoginState.update { state -> state.copy(username = username) }

    fun onPasswordChanged(password: String) =
        _agenLoginState.update { state -> state.copy(password = password) }

    fun login() {
        viewModelScope.launch {
            viewModelScope.launch {
                _loginState.update { LoginState.Loading }
                val data = loginAgenUseCase.invoke(
                    _agenLoginState.value.username,
                    _agenLoginState.value.password
                )
                when (data) {
                    is DataResult.Error -> {

                        _loginState.update {
                            LoginState.Error(
                                when (data.error) {
                                    HttpErrorCode.BAD_REQUEST -> ""
                                    HttpErrorCode.UNAUTHORIZED -> ""
                                    HttpErrorCode.FORBIDDEN -> ""
                                    HttpErrorCode.NOT_FOUND -> ""
                                    HttpErrorCode.TIMEOUT -> ""
                                    HttpErrorCode.INTERNAL_SERVER_ERROR -> ""
                                    HttpErrorCode.UNKNOWN -> ""
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

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val isLoggedIn: Boolean) : LoginState()
    data class Error(val message: String) : LoginState()
}
