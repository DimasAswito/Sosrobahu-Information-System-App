package com.polije.sosrobahufactoryapp.data.model.sales

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class DeleteKunjunganResponse(

	@SerialName("message")
	val message: String
)
