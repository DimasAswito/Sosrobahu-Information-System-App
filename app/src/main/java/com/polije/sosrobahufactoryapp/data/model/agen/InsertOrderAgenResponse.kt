package com.polije.sosrobahufactoryapp.data.model.agen

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InsertOrderAgenResponse(

    @SerialName("id_order")
    val idOrder: Int? = null,

    @SerialName("message")
    val message: String? = null,

    @SerialName("order_details")
    val orderDetails: List<OrderDetailsItem> = emptyList()
)

@Serializable
data class OrderDetailsItem(

    @SerialName("harga_tetap_nota")
    val hargaTetapNota: Int? = null,

    @SerialName("id_master_barang")
    val idMasterBarang: Int? = null,

    @SerialName("id_order")
    val idOrder: Int? = null,

    @SerialName("id_user_distributor")
    val idUserDistributor: Int? = null,

    @SerialName("id_barang_distributor")
    val idBarangDistributor: Int? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null,

    @SerialName("jumlah_produk")
    val jumlahProduk: String? = null,

    @SerialName("jumlah_harga_item")
    val jumlahHargaItem: Int? = null,

    @SerialName("id_user_agen")
    val idUserAgen: Int? = null,

    @SerialName("created_at")
    val createdAt: String? = null
)
