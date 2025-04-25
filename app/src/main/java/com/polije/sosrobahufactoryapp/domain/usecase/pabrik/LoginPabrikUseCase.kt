package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.utils.DataResult

class LoginPabrikUseCase(private val pabrikRepository: PabrikRepository) {
    suspend operator fun invoke(username: String, password : String) : DataResult<LoginResponse, String>{
        if (username.isBlank() || password.isBlank()) {
            return DataResult.Error("Tidak Valid" ,"Username atau password tidak boleh kosong")
        }

        return pabrikRepository.login(username, password)
    }
}