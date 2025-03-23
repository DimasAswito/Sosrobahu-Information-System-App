package com.polije.sosrobahufactoryapp.domain.pabrik.usecase

import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.utils.DataResult

class LoginUseCase(private val pabrikRepository: PabrikRepository) {
    suspend operator fun invoke(username: String, password : String) : DataResult<LoginResponse, String>{
        if (username.isBlank() || password.isBlank()) {
            return DataResult.Error("Tidak Valid" ,"Username atau password tidak boleh kosong")
        }

        return pabrikRepository.login(username, password)
    }
}