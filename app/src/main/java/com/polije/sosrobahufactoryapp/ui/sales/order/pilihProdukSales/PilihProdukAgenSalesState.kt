package com.polije.sosrobahufactoryapp.ui.sales.order.pilihProdukSales

import com.polije.sosrobahufactoryapp.data.model.sales.ListBarangAgenSalesResponse

data class PilihProdukAgenSalesState(
    val isLoading: Boolean = false,
    val data: ListBarangAgenSalesResponse? = null,
    val errorMessage: String? = null
)