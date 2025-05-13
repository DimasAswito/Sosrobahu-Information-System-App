package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.data.model.sales.TambahTokoRequest
import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository

class TambahTokoUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(
        namaToko: String,
        namaPemilik: String,
        noTelp: String,
        lokasi: String
    ) = repository.tambahToko(
        TambahTokoRequest(namaToko, lokasi, namaPemilik, noTelp)
    )
}