package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewBarangRequest(
    @SerialName("products")
    val products: List<Int>
)