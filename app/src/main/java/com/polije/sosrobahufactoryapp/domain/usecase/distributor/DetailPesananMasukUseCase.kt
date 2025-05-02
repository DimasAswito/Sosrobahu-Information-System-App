package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository

class DetailPesananMasukUseCase(private val distributorRepository: DistributorRepository) {
    suspend operator fun invoke(idOrder: Int) = distributorRepository.getDetailPesananMasuk(idOrder)
}