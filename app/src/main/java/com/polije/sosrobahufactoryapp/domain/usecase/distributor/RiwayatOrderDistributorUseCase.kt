package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository

class RiwayatOrderDistributorUseCase(private val distributorRepository: DistributorRepository) {
    operator fun invoke() = distributorRepository.getRiwayatOrderDistributor()
}