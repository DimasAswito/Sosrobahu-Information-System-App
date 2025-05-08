package com.polije.sosrobahufactoryapp.ui.agen.pesanan.detailPesanan

import com.polije.sosrobahufactoryapp.data.model.agen.DetailPesananMasukAgenResponse

data class DetailPesananAgenState(
    val isLoading: Boolean = false,
    val isSubmitted: Boolean = false,
    val data: DetailPesananMasukAgenResponse? = null,
    val errorMessage: String? = null
)