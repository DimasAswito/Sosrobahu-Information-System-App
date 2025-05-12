package com.polije.sosrobahufactoryapp.data.model.agen

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class DetailPesananMasukAgenResponse(

	@SerialName("listBarangAgen")
	val dataDetail: DetailPesananAgenData,

	@SerialName("success")
	val success: Boolean,

	@SerialName("message")
	val message: String
)

@Serializable
data class ItemNotaDetailPesananAgenItem(

	@SerialName("jumlah_harga")
	val jumlahHarga: Int,

	@SerialName("nama_rokok")
	val namaRokok: String,

	@SerialName("harga_satuan")
	val hargaSatuan: Int,

	@SerialName("jumlah_item")
	val jumlahItem: Int
)

@Serializable
data class DetailPesananAgenData(

	@SerialName("total_item")
	val totalItem: Int,

	@SerialName("id_order")
	val idOrder: Int,

	@SerialName("nama_sales")
	val namaSales: String,

	@SerialName("item_nota")
	val itemNota: List<ItemNotaDetailPesananAgenItem>,

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
