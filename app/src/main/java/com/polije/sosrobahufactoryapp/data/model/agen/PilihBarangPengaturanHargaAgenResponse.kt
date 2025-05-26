package com.polije.sosrobahufactoryapp.data.model.agen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PilihBarangPengaturanHargaAgenResponse(

    @SerialName("rokokAgens")
    val rokokAgens: List<RokokAgensItem> = emptyList(),

    @SerialName("newProductsCount")
    val newProductsCount: Int
)

@Parcelize
@Serializable
data class RokokAgensItem(

    @SerialName("id_master_barang")
    val idMasterBarang: Int,

    @SerialName("nama_rokok")
    val namaRokok: String,

    @SerialName("gambar")
    val gambar: String,

    @SerialName("harga")
    val harga: Int,

    @SerialName("harga_pabrik")
    val hargaPabrik: Int,

    @SerialName("id")
    val id: Int
) : Parcelable
