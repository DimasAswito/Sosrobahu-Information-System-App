package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository

class TokenUseCase(private val repository: PabrikRepository) {
    suspend fun getToken() : String = repository.getToken()
}