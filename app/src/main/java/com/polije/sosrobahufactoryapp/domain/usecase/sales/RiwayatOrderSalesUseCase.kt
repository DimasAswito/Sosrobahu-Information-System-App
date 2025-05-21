package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository

class RiwayatOrderSalesUseCase(private val repository: SalesRepository) {
    operator fun invoke() = repository.getOrderSales()
}