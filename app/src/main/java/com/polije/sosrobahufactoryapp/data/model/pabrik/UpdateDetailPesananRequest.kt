package com.polije.sosrobahufactoryapp.data.model.pabrik

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDetailPesananRequest(
    @SerialName("status")
    val status: Int
)

