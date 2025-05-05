package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import android.net.Uri
import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor

class OrderDistributorUseCase(private val repository: DistributorRepository) {
    suspend operator fun invoke(
        products: List<SelectedProdukDistributor>,
        totalAmount: Int,
        buktiUri: Uri
    ) = repository.orderBarang(products, totalAmount, buktiUri)
}