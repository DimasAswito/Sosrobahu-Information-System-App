package com.polije.sosrobahufactoryapp.domain.usecase.sales

import android.net.Uri
import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository

class TambahKunjunganTokoUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(idToko: Int, tanggal: String, sisaStok: Int, buktiKunjungan: Uri) =
        repository.insertKunjunganToko(
            idToko,
            tanggal,
            buktiKunjungan = buktiKunjungan,
            sisaProduk = sisaStok
        )
}