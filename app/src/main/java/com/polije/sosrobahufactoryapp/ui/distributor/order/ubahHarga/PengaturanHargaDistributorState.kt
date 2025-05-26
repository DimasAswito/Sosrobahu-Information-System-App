package com.polije.sosrobahufactoryapp.ui.distributor.order.ubahHarga

import com.polije.sosrobahufactoryapp.data.model.distributor.DistributorBarangItems
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPengaturanHargaResponse

data class PengaturanHargaDistributorState(
    val data: List<DistributorBarangItems> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage : String? = null
)
