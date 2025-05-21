package com.polije.sosrobahufactoryapp.utils

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val isLoggedIn: Boolean) : LoginState()
    data class Error(val message: String) : LoginState()
}
