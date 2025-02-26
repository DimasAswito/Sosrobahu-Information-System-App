package com.polije.sosrobahufactoryapp.ui.factory.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class FactoryLoginViewModel : ViewModel() {
    private val _username = MutableStateFlow("")
    private val _passwordState = MutableStateFlow("")

    val isButtonEnabled: StateFlow<Boolean> =
        combine(_username, _passwordState) { email: String, password: String ->
            email.isNotBlank() && password.isNotEmpty()
        }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onUsernameChanged(email: String) {
        _username.value = email
    }

    fun onPasswordChanged(password: String) {
        _passwordState.value = password
    }

}