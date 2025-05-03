package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PilihBarangPabrikDistributorResponse(

    @SerialName("PilihBarangPabrikDistributorResponse")
    val pilihBarangPabrikDistributorResponse: List<PilihBarangPabrikDistributorResponseItem> = emptyList()
)

@Serializable
data class PilihBarangPabrikDistributorResponseItem(

    @SerialName("id_master_barang")
    val idMasterBarang: Int? = null,

    @SerialName("nama_rokok")
    val namaRokok: String? = null,

    @SerialName("gambar")
    val gambar: String? = null
)
