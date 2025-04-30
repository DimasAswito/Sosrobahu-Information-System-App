package com.polije.sosrobahufactoryapp.ui.factory.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.LoginPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.UserSessionPabrikUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.LoginState
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FactoryLoginViewModel(
    private val loginPabrikUseCase: LoginPabrikUseCase,
    userSessionPabrikUseCase: UserSessionPabrikUseCase
) : ViewModel() {

    private val _factoryLoginInput = MutableStateFlow(FactoryLoginInput())

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState.asStateFlow()

    val isAlreadyLoggedIn: StateFlow<Boolean> = userSessionPabrikUseCase.invoke().map { session ->
        session.role == UserRole.PABRIK && session.token?.isBlank() == false
    }.distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val isValid = _factoryLoginInput.map { state ->
        state.username.isNotBlank() && state.password.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun onUsernameChanged(username: String) =
        _factoryLoginInput.update { state -> state.copy(username = username) }

    fun onPasswordChanged(password: String) =
        _factoryLoginInput.update { state -> state.copy(password = password) }


    fun login() {
        viewModelScope.launch {
            _loginState.update { LoginState.Loading }
            val data = loginPabrikUseCase.invoke(
                _factoryLoginInput.value.username,
                _factoryLoginInput.value.password
            )
            when (data) {
                is DataResult.Error -> {

                    _loginState.update {
                        when (data.error) {
                            HttpErrorCode.BAD_REQUEST -> LoginState.Error("")
                            HttpErrorCode.UNAUTHORIZED -> LoginState.Error("")
                            HttpErrorCode.FORBIDDEN -> LoginState.Error("")
                            HttpErrorCode.NOT_FOUND -> LoginState.Error("")
                            HttpErrorCode.TIMEOUT -> LoginState.Error("")
                            HttpErrorCode.INTERNAL_SERVER_ERROR -> LoginState.Error("")
                            HttpErrorCode.UNKNOWN -> LoginState.Error("")
                        }
                    }
                }

                is DataResult.Success -> {
                    _loginState.update { LoginState.Success(true) }
                }
            }
        }
    }

}