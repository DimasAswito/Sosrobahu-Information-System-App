package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository

class UploadNewBarangDistributorUseCase(private val repository: DistributorRepository) {
    suspend operator fun invoke(ids: List<Int>) = repository.uploadBarangTerbaru(ids)
}