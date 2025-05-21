package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.data.model.sales.TambahTokoRequest
import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository

class UpdateTokoUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(
        idToko: Int,
        namaToko: String,
        namaPemilik: String,
        noTelp: String,
        lokasi: String
    ) = repository.updateToko(idToko, TambahTokoRequest(namaToko, lokasi, namaPemilik, noTelp))
}