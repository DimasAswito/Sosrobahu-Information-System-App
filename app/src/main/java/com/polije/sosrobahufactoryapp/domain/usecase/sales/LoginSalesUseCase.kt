package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

class LoginSalesUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(
        username: String,
        password: String
    ): DataResult<LoginResponse, HttpErrorCode> =
        repository.login(username = username, password = password)
}