package com.polije.sosrobahufactoryapp.domain.usecase.agen

import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository

class PilihBarangDistributorAgenUseCase(private val repository: AgenRepository) {
    suspend operator fun invoke() = repository.pilihBarangAgen()
}