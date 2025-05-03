package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository

class PilihProdukPabrikDistributorUseCase(private val distributorRepository: DistributorRepository) {
    suspend operator fun invoke() = distributorRepository.pilihBarangPabrik()
}