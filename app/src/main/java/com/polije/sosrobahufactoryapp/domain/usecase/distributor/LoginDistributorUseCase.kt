package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.DistributorRepository
import com.polije.sosrobahufactoryapp.utils.DataResult

class LoginDistributorUseCase(private val distributorRepository: DistributorRepository) {
    suspend operator fun invoke(
        userName: String,
        password: String
    ): DataResult<LoginResponse, String> {
        if (userName.isBlank() || password.isBlank()) {
            return DataResult.Error("Tidak Valid", "Username atau password tidak boleh kosong")
        }

        return distributorRepository.login(userName, password)
    }
}