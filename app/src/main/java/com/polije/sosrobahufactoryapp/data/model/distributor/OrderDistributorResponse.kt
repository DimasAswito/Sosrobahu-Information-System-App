package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OrderDistributorResponse(

	@SerialName("success")
	val success: Boolean? = null,

	@SerialName("message")
	val message: String? = null,

	@SerialName("order_details")
	val orderDetails: List<OrderDistributorDetailsItem> = emptyList(),

	@SerialName("order")
	val order: DistributorOrder? = null
)
@Serializable
data class OrderDistributorDetailsItem(

	@SerialName("id_master_barang")
	val idMasterBarang: String? = null,

	@SerialName("id_order")
	val idOrder: Int? = null,

	@SerialName("id_user_distributor")
	val idUserDistributor: Int? = null,

	@SerialName("updated_at")
	val updatedAt: String? = null,

	@SerialName("jumlah_produk")
	val jumlahProduk: String? = null,

	@SerialName("jumlah_harga_item")
	val jumlahHargaItem: Int? = null,

	@SerialName("created_at")
	val createdAt: String? = null,

	@SerialName("id_user_pabrik")
	val idUserPabrik: Int? = null
)

@Serializable
data class DistributorOrder(

	@SerialName("status_pemesanan")
	val statusPemesanan: Int? = null,

	@SerialName("total")
	val total: String? = null,

	@SerialName("id_order")
	val idOrder: Int? = null,

	@SerialName("id_user_distributor")
	val idUserDistributor: Int? = null,

	@SerialName("jumlah")
	val jumlah: String? = null,

	@SerialName("updated_at")
	val updatedAt: String? = null,

	@SerialName("created_at")
	val createdAt: String? = null,

	@SerialName("bukti_transfer")
	val buktiTransfer: String? = null,

	@SerialName("tanggal")
	val tanggal: String? = null
)
