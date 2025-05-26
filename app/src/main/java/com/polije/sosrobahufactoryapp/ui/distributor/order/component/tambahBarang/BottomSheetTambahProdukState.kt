package com.polije.sosrobahufactoryapp.ui.distributor.order.component.tambahBarang

import com.polije.sosrobahufactoryapp.data.model.distributor.GetBarangTerbaruPabrikDistributorDataItem

data class BottomSheetTambahProdukState(
    val listBarang: List<GetBarangTerbaruPabrikDistributorDataItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSubmitted : Boolean = false
)