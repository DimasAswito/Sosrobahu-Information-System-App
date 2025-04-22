package com.polije.sosrobahufactoryapp.data.model.pabrik

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class InsertRestockResponse(

	@SerialName("message")
	val message: String,

	@SerialName("status")
	val status: String,

	@SerialName("success")
	val success: Boolean
)
