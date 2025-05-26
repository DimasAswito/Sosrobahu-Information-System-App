package com.polije.sosrobahufactoryapp.data.model.agen

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TambahBarangTerbaruAgenResponse(

    @SerialName("data")
    val data: List<TambahBarangTerbaruAgenDataItem>,

    @SerialName("message")
    val message: String,

    @SerialName("added_count")
    val addedCount: Int
)

@Serializable
data class TambahBarangTerbaruAgenDataItem(

    @SerialName("id_master_barang")
    val idMasterBarang: Int,

    @SerialName("harga_agen")
    val hargaAgen: Int,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("id_user_agen")
    val idUserAgen: Int,

    @SerialName("stok_karton")
    val stokKarton: Int,

    @SerialName("created_at")
    val createdAt: String
)
