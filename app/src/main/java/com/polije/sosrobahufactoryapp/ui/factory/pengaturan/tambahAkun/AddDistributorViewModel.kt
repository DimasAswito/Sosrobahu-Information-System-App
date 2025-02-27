package com.polije.sosrobahufactoryapp.ui.factory.pengaturan.tambahAkun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AddDistributorViewModel : ViewModel() {
    private val _addDistributorState = MutableStateFlow(AddDistributorState())
    val addDistributorState = _addDistributorState.asStateFlow()

    val isValid: StateFlow<Boolean> = _addDistributorState
        .map { state ->
            state.fullName.isNotBlank() && state.userName.isNotBlank() && state.password.isNotBlank() && state.phoneNumber.isNotBlank() && state.NIK.isNotBlank()
        }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onFullNameChanged(fullName: String) {
        _addDistributorState.update {
            it.copy(fullName = fullName)
        }
    }

    fun onUsernameChanged(username: String) {
        _addDistributorState.update {
            it.copy(userName = username)
        }
    }

    fun onPasswordChanged(password: String) {
        _addDistributorState.update {
            it.copy(password = password)
        }
    }

    fun onPhoneNumberChanged(value: String) {
        _addDistributorState.update {
            it.copy(phoneNumber = value)
        }
    }

    fun onNIKChanged(nik: String) {
        _addDistributorState.update {
            it.copy(NIK = nik)
        }
    }
}

