package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository

class UpdateBarangHargaDistributorUseCase(private val repository: DistributorRepository) {
    suspend operator fun invoke(id: Int, newHarga: Int) =
        repository.updateBarangHarga(id, newPrice = newHarga)
}