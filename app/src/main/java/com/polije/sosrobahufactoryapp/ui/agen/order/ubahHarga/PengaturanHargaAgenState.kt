package com.polije.sosrobahufactoryapp.ui.agen.order.ubahHarga

import com.polije.sosrobahufactoryapp.data.model.agen.RokokAgensItem

data class PengaturanHargaAgenState(
    val list: List<RokokAgensItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage : String? = null
)