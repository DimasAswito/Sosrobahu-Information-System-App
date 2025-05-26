package com.polije.sosrobahufactoryapp.data.model.agen

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UpdateBarangPengaturanHargaAgenResponse(

	@SerialName("data")
	val data: Data,

	@SerialName("message")
	val message: String
)

@Serializable
data class Data(

	@SerialName("harga_agen")
	val hargaAgen: Int,

	@SerialName("id")
	val id: Int
)
