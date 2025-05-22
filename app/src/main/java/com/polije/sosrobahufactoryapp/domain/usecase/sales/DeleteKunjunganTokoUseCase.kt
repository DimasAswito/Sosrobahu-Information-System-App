package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository

class DeleteKunjunganTokoUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(idKunjunganToko: Int) =
        repository.deleteKunjunganToko(idKunjunganToko)
}