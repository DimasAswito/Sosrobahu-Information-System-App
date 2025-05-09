package com.polije.sosrobahufactoryapp.domain.usecase.agen

import android.net.Uri
import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository
import com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen.SelectedProdukAgen

class OrderAgenUseCase(private val repository: AgenRepository) {
    suspend operator fun invoke(
        products: List<SelectedProdukAgen>,
        totalAmount: Int,
        buktiUri: Uri
    ) = repository.orderBarang(products, totalAmount, buktiUri)
}