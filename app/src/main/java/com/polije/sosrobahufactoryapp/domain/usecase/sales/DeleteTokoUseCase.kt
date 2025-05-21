package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository

class DeleteTokoUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(idToko: Int) = repository.deleteToko(idToko)
}