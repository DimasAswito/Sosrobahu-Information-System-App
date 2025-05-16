package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UpdateStatusOrderDistributor(

	@SerialName("data")
	val data: UpdateStatusOrderDistributorData,

	@SerialName("success")
	val success: Boolean,

	@SerialName("message")
	val message: String
)

@Serializable
data class UpdateStatusOrderDistributorData(

	@SerialName("status_pemesanan")
	val statusPemesanan: Int,

	@SerialName("id_order")
	val idOrder: Int
)
