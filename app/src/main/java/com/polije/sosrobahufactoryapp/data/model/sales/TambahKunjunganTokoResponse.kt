package com.polije.sosrobahufactoryapp.data.model.sales

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TambahKunjunganTokoResponse(

	@SerialName("data")
	val data: Data? = null,

	@SerialName("message")
	val message: String? = null
)

@Serializable
data class Data(

	@SerialName("id_daftar_toko")
	val idDaftarToko: String? = null,

	@SerialName("id_user_sales")
	val idUserSales: Int? = null,

	@SerialName("tanggal")
	val tanggal: String? = null,

	@SerialName("sisa_produk")
	val sisaProduk: String? = null,

	@SerialName("gambar")
	val gambar: String? = null,

	@SerialName("id_kunjungan_toko")
	val idKunjunganToko: Int? = null
)
