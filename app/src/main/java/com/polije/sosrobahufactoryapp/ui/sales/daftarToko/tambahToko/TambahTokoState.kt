package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.tambahToko

import com.polije.sosrobahufactoryapp.data.model.sales.TambahTokoRequest

data class TambahTokoState(
    val isSubmitted: Boolean = false,
    val isLoading : Boolean = false,
    val errorMessage: String? = null,
    val namaToko: String = "",
    val namaPemilik: String = "",
    val noTelp: String = "",
    val lokasi: String = ""
) {
    val isValid: Boolean
        get() = namaToko.isNotBlank()
                && namaPemilik.isNotBlank()
                && noTelp.isNotBlank()
                && lokasi.isNotBlank()
}