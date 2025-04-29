package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository

class LogoutPabrikUseCase(private val pabrikRepository: PabrikRepository) {
    suspend operator fun invoke() {
        pabrikRepository.logout()
    }
}