package com.polije.sosrobahufactoryapp.ui.distributor.pesanan.detailPesanan

import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse

data class DetailPesananDistributorState(
    val isLoading: Boolean = false,
    val isSubmitted: Boolean = false,
    val data: DetailPesananMasukDistributorResponse? = null,
    val errorMessage: String? = null
)