package com.polije.sosrobahufactoryapp.ui.distributor.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.LoginDistributorUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DistributorLoginViewModel(val loginDistributorUseCase: LoginDistributorUseCase) :
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
                                data.message
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
