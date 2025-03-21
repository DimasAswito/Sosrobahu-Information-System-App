package com.polije.sosrobahufactoryapp.domain.pabrik.usecase

import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository

class TokenUseCase(private val repository: PabrikRepository) {
    suspend fun getToken() : String = repository.getToken()
}