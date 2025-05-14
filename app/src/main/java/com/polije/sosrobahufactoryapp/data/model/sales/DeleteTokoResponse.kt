package com.polije.sosrobahufactoryapp.data.model.sales

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class DeleteTokoResponse(

	@SerialName("message")
	val message: String
)
