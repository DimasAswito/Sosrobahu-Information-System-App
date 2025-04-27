package com.polije.sosrobahufactoryapp.domain.usecase.agen

import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository
import com.polije.sosrobahufactoryapp.utils.DataResult

class LoginAgenUseCase(private val agenRepository: AgenRepository) {
    suspend operator fun invoke(username : String, password : String) : DataResult<LoginResponse, String> = agenRepository.login(username = username,password = password)
}