package com.polije.sosrobahufactoryapp.ui.factory.pengaturan.tambahAkun

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddDistributorViewModel : ViewModel() {
    private val _addDistributorState = MutableStateFlow(AddDistributorState())
    val addDistributorState = _addDistributorState.asStateFlow()

    fun onFullNameChanged(fullName: String) {
        _addDistributorState.update {
            it.copy(fullName = fullName)
        }
    }
}

data class AddDistributorState(
    val fullName: String = "",
    val userName: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val NIK: String = "",
    val isValid: Boolean = false
)