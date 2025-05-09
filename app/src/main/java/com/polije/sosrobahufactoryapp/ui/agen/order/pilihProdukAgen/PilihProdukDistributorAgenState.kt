package com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen

import com.polije.sosrobahufactoryapp.data.model.agen.PilihBarangDistributorAgenResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPabrikDistributorResponse

data class PilihProdukDistributorAgenState(
    val isLoading: Boolean = false,
    val data: PilihBarangDistributorAgenResponse? = null,
    val errorMessage: String? = null
)