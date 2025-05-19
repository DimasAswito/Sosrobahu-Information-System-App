package com.polije.sosrobahufactoryapp.ui.sales.order.detailOrder

data class DetailOrderSalesState(
    val isLoading: Boolean = false,
    val isSubmitted: Boolean = false,
    val errorMessage: String? = null
)