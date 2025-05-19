package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository

class DownloadNotaDistributorUseCase(private val repository: DistributorRepository) {
    suspend operator fun invoke(idNota: Int) = repository.downloadNota(idNota)
}