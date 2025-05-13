package com.polije.sosrobahufactoryapp.data.model.sales

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TambahTokoResponse(

	@SerialName("store")
	val store: Store,

	@SerialName("message")
	val message: String
)

@Serializable
data class Store(

	@SerialName("id_daftar_toko")
	val idDaftarToko: Int,

	@SerialName("nama_pemilik")
	val namaPemilik: String,

	@SerialName("lokasi")
	val lokasi: String,

	@SerialName("id_user_sales")
	val idUserSales: Int,

	@SerialName("no_telp")
	val noTelp: String,

	@SerialName("nama_toko")
	val namaToko: String
)
