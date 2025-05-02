package com.polije.sosrobahufactoryapp.ui.distributor.order.detailOrder

import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse

data class DetailOrderDistributorState(
    val isLoading: Boolean = false,
    val data: DetailPesananMasukDistributorResponse? = null,
    val error: String? = null
)
