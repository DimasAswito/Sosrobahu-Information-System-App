package com.polije.sosrobahufactoryapp.domain.usecase.agen

import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository

class UpdateBarangHargaAgenUseCase(private val repository: AgenRepository) {
    suspend operator fun invoke(id: Int, newHarga: Int) =
        repository.updateBarangHarga(id, newPrice = newHarga)
}