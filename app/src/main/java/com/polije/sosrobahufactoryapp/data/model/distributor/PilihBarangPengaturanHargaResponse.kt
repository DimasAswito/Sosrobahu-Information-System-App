package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PilihBarangPengaturanHargaResponse(

    @SerialName("distributors")
    val distributors: List<DistributorBarangItems> = emptyList(),

    @SerialName("new_products_count")
    val newProductsCount: Int
)

@Serializable
data class DistributorBarangItems(

    @SerialName("id_master_barang")
    val idMasterBarang: Int,

    @SerialName("nama_rokok")
    val namaRokok: String,

    @SerialName("harga")
    val harga: Int,

    @SerialName("harga_pabrik")
    val hargaPabrik: Int,

    @SerialName("id")
    val id: Int
)
