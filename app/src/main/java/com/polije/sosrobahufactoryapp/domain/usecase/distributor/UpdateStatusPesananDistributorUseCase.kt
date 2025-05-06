package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository

class UpdateStatusPesananDistributorUseCase(private val repository : DistributorRepository) {
    suspend operator fun invoke(idOrder: Int, status : Int) = repository.updateStatusPesanan(idOrder,status)
}