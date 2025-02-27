package com.polije.sosrobahufactoryapp.ui.distributor.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class DistributorLoginViewModel : ViewModel() {
    private var _distributorLoginState = MutableStateFlow(DistributorLoginState())

    val isValid = _distributorLoginState.map { state ->
        state.username.isNotBlank() && state.password.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onUsernameChanged(username: String) =
        _distributorLoginState.update { state -> state.copy(username = username) }

    fun onPasswordChanged(password: String) =
        _distributorLoginState.update { state -> state.copy(password = password) }


}