package com.polije.sosrobahufactoryapp.data.model.sales

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderSalesResponse(

    @SerialName("message")
    val message: String,

    @SerialName("order_id")
    val orderId: Int,

    @SerialName("order_details")
    val orderDetails: List<OrderDetailsItem>
)

@Serializable
data class OrderDetailsItem(

    @SerialName("harga_tetap_nota")
    val hargaTetapNota: Int,

    @SerialName("id_master_barang")
    val idMasterBarang: Int,

    @SerialName("id_order")
    val idOrder: Int,

    @SerialName("id_barang_agen")
    val idBarangAgen: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("jumlah_produk")
    val jumlahProduk: String,

    @SerialName("id_user_sales")
    val idUserSales: Int,

    @SerialName("jumlah_harga_item")
    val jumlahHargaItem: Int,

    @SerialName("id_user_agen")
    val idUserAgen: Int,

    @SerialName("created_at")
    val createdAt: String
)
