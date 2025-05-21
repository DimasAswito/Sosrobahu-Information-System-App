package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository

class KunjunganTokoUseCase(private val repository: SalesRepository) {
    operator fun invoke(idToko: Int) = repository.getKunjunganToko(idToko)
}