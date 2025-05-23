package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository

class ListBarangPengaturanHargaUseCase(private val repository: DistributorRepository) {
    suspend operator fun invoke() = repository.getBarangPengaturanHarga()
}