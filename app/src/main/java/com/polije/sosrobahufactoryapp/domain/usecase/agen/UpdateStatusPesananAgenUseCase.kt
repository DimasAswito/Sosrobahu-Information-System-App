package com.polije.sosrobahufactoryapp.domain.usecase.agen

import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository

class UpdateStatusPesananAgenUseCase(private val repository: AgenRepository) {
    suspend operator fun invoke(idOrder: Int, status: Int) =
        repository.updateStatusPesanan(idOrder, status)
}