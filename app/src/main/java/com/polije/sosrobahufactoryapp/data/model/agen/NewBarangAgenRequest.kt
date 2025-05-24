package com.polije.sosrobahufactoryapp.data.model.agen

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewBarangAgenRequest(
    @SerialName("products")
    val products: List<Int>
)