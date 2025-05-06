package com.polije.sosrobahufactoryapp.domain.usecase.agen

import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository

class DashboardAgenUseCase(private val agenRepository: AgenRepository) {
    suspend operator fun invoke() = agenRepository.getDashboardAgen()

}