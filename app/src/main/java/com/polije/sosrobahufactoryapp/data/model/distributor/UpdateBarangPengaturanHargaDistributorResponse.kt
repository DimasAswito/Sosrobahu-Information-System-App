package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UpdateBarangPengaturanHargaDistributorResponse(

	@SerialName("data")
	val data: Data,

	@SerialName("message")
	val message: String
)

@Serializable
data class Data(

	@SerialName("id")
	val id: Int,

	@SerialName("harga_distributor")
	val hargaDistributor: Int
)
