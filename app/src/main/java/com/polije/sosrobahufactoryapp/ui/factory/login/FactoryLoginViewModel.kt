package com.polije.sosrobahufactoryapp.ui.factory.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.LoginUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FactoryLoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginResult = MutableStateFlow<String?>(null)
    val loginResult: StateFlow<String?> get() = _loginResult.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val data = loginUseCase.invoke(username, password)
            when (data) {
                is DataResult.Error -> _loginResult.value = data.error
                is DataResult.Success -> _loginResult.value = data.data.token?.plainTextToken ?: ""
            }
        }
    }

}
