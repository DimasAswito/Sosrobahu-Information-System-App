package com.polije.sosrobahufactoryapp.domain.usecase.agen

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class InsertOrderAgenResponse(

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

	@SerialName("id_user_distributor")
	val idUserDistributor: Int,

	@SerialName("id_barang_distributor")
	val idBarangDistributor: Int,

	@SerialName("updated_at")
	val updatedAt: String,

	@SerialName("jumlah_produk")
	val jumlahProduk: String,

	@SerialName("jumlah_harga_item")
	val jumlahHargaItem: Int,

	@SerialName("id_user_agen")
	val idUserAgen: Int,

	@SerialName("created_at")
	val createdAt: String
)
