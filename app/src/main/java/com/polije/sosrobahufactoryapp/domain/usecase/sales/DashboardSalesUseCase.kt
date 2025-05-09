package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository

class DashboardSalesUseCase(private val repository : SalesRepository) {
    suspend operator fun invoke() = repository.getDashboardSales()
}