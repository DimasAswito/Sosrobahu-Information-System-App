package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository

class TokoSalesUseCase(private val repository: SalesRepository) {
     operator fun invoke() = repository.getListTokoSales()
}