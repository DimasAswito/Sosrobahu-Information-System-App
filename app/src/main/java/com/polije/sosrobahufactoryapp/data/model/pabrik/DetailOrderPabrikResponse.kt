package com.polije.sosrobahufactoryapp.data.model.pabrik

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class DetailOrderPabrikResponse(

	@SerialName("total_item")
	val totalItem: Int,

	@SerialName("id_order")
	val idOrder: Int,

	@SerialName("nama_distributor")
	val namaDistributor: String,

	@SerialName("item_nota")
	val itemNota: List<ItemNotaItem>,

	@SerialName("total_harga")
	val totalHarga: Int,

	@SerialName("tanggal")
	val tanggal: String,

	@SerialName("no_telp")
	val noTelp: String,

	@SerialName("gambar")
	val gambar: String,

	@SerialName("status")
	val status: Int
)

@Serializable
data class ItemNotaItem(

	@SerialName("jumlah_harga")
	val jumlahHarga: Int,

	@SerialName("nama_rokok")
	val namaRokok: String,

	@SerialName("harga_satuan")
	val hargaSatuan: Int,

	@SerialName("jumlah_item")
	val jumlahItem: Int
)
