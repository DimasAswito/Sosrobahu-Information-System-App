package com.polije.sosrobahufactoryapp.ui.agen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AgenLoginViewModel : ViewModel() {
    private var _agenLoginViewModel = MutableStateFlow(AgenLoginState())

    val isValid: StateFlow<Boolean> = _agenLoginViewModel.map { state ->
        state.password.isNotBlank() && state.username.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onUsernameChanged(username: String) =
        _agenLoginViewModel.update { state -> state.copy(username = username) }

    fun onPasswordChanged(password: String) =
        _agenLoginViewModel.update { state -> state.copy(password = password) }

}