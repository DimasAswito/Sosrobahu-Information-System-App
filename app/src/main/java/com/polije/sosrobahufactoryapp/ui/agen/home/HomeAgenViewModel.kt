package com.polije.sosrobahufactoryapp.ui.agen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.agen.UserSessionAgenUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeAgenViewModel(private val userSessionAgenUseCase: UserSessionAgenUseCase) : ViewModel() {


    val isLogged =
        userSessionAgenUseCase.isLoggingIn()
            .stateIn(viewModelScope, SharingStarted.Eagerly, true)
}