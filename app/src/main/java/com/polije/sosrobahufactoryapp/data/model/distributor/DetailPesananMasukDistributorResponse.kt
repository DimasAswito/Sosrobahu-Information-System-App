package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class DetailPesananMasukDistributorResponse(

	@SerialName("total_item")
	val totalItem: Int? = null,

	@SerialName("id_order")
	val idOrder: Int? = null,

	@SerialName("item_nota")
	val itemNota: List<ItemNotaItem> = emptyList(),

	@SerialName("nama_agen")
	val namaAgen: String? = null,

	@SerialName("total_harga")
	val totalHarga: Int? = null,

	@SerialName("tanggal")
	val tanggal: String? = null,

	@SerialName("no_telp")
	val noTelp: String? = null,

	@SerialName("gambar")
	val gambar: String? = null,

	@SerialName("status")
	val status: Int? = null
)

@Serializable
data class ItemNotaItem(

	@SerialName("jumlah_harga")
	val jumlahHarga: Int? = null,

	@SerialName("nama_rokok")
	val namaRokok: String? = null,

	@SerialName("harga_satuan")
	val hargaSatuan: Int? = null,

	@SerialName("jumlah_item")
	val jumlahItem: Int? = null
)
