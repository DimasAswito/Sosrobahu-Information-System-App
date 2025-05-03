package com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor

import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPabrikDistributorResponse

data class PilihProdukDistributorState(
    val isLoading: Boolean = false,
    val data: PilihBarangPabrikDistributorResponse? = null,
    val errorMessage: String? = null
)