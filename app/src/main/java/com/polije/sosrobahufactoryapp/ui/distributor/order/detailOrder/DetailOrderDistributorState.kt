package com.polije.sosrobahufactoryapp.ui.distributor.order.detailOrder

data class DetailOrderDistributorState(
    val isLoading: Boolean = false,
    val isSubmitted: Boolean = false,
    val errorMessage: String? = null
)