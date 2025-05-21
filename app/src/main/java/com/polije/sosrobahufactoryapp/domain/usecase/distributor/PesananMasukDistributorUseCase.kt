package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository

class PesananMasukDistributorUseCase(private val distributorRepository: DistributorRepository) {
    operator fun invoke() = distributorRepository.getPesananMasukDistributor()
}