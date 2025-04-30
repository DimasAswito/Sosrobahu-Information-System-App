package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository

class DasbhoardDistributorUseCase(private val distributorRepository: DistributorRepository) {
    suspend operator fun invoke() = distributorRepository.getDashboardDistributor()
}