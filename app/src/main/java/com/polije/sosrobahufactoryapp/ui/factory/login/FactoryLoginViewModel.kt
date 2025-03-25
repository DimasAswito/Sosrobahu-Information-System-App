package com.polije.sosrobahufactoryapp.ui.factory.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.LoginUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FactoryLoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _factoryLoginInput = MutableStateFlow(FactoryLoginInput())

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState.asStateFlow()

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
            val data = loginUseCase.invoke(
                _factoryLoginInput.value.username,
                _factoryLoginInput.value.password
            )
            when (data) {
                is DataResult.Error -> {

                    _loginState.update { LoginState.Error(data.message) }
                }

                is DataResult.Success -> {
                    _loginState.update { LoginState.Success(true) }
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
