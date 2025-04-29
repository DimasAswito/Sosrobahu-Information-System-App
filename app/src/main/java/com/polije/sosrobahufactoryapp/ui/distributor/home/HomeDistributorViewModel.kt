package com.polije.sosrobahufactoryapp.ui.distributor.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.LogoutDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.UserSessionDistributorUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeDistributorViewModel(
    userSessionDistributorUseCase: UserSessionDistributorUseCase,
    private val logoutDistributorUseCase: LogoutDistributorUseCase
) :
    ViewModel() {

    val isLogged =
        userSessionDistributorUseCase.isLoggingIn()
            .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun logout() {
        viewModelScope.launch {
            logoutDistributorUseCase.invoke()
        }
    }
}