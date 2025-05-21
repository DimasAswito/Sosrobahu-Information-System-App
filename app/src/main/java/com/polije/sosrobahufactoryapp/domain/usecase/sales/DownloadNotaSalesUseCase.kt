package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository

class DownloadNotaSalesUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(idNota: Int) = repository.downloadNota(idNota)
}