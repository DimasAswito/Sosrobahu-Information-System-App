package com.polije.sosrobahufactoryapp.ui.agen.order.component.tambahBarang

import com.polije.sosrobahufactoryapp.data.model.agen.GetBarangTerbaruDistributorAgenDataItem

data class BottomSheetTambahProdukAgenState(
    val listBarang: List<GetBarangTerbaruDistributorAgenDataItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSubmitted: Boolean = false
)