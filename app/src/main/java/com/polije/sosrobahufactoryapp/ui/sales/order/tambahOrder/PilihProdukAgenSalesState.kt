package com.polije.sosrobahufactoryapp.ui.sales.order.tambahOrder

import com.polije.sosrobahufactoryapp.data.model.agen.PilihBarangDistributorAgenResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPabrikDistributorResponse

data class PilihProdukAgenSalesState(
    val isLoading: Boolean = false,
    val data: PilihBarangDistributorAgenResponse? = null,
    val errorMessage: String? = null
)