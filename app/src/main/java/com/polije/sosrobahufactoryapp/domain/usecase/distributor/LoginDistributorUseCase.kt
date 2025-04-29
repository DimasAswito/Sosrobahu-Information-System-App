package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

class LoginDistributorUseCase(private val distributorRepository: DistributorRepository) {
    suspend operator fun invoke(
        userName: String,
        password: String
    ): DataResult<LoginResponse, HttpErrorCode> {
        if (userName.isBlank() || password.isBlank()) {
            return DataResult.Error(HttpErrorCode.UNAUTHORIZED)
        }

        return distributorRepository.login(userName, password)
    }
}