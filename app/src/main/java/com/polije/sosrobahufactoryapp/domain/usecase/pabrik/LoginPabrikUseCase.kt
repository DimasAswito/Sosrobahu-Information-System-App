package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

class LoginPabrikUseCase(private val pabrikRepository: PabrikRepository) {
    suspend operator fun invoke(
        username: String,
        password: String
    ): DataResult<LoginResponse, HttpErrorCode> {
        return pabrikRepository.login(username, password)
    }
}