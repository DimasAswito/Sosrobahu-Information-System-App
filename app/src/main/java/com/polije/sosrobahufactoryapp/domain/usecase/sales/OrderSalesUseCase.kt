package com.polije.sosrobahufactoryapp.domain.usecase.sales

import android.net.Uri
import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository
import com.polije.sosrobahufactoryapp.ui.sales.order.pilihProdukSales.SelectedProdukSales

class OrderSalesUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(
        products: List<SelectedProdukSales>,
        totalAmount: Int,
        buktiUri: Uri
    ) = repository.orderBarang(products, totalAmount, buktiUri)
}