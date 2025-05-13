package com.polije.sosrobahufactoryapp.data.model.sales

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TambahTokoRequest(
    @SerialName("nama_toko")
    val namaToko: String,

    @SerialName("lokasi")
    val lokasi: String,

    @SerialName("nama_pemilik")
    val namaPemilik: String,

    @SerialName("no_telp")
    val noTelp: String
)
