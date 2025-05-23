package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetBarangTerbaruPabrikDistributorResponse(

    @SerialName("data")
    val data: List<GetBarangTerbaruPabrikDistributorDataItem> = emptyList(),

    @SerialName("success")
    val success: Boolean,

    @SerialName("count")
    val count: Int
)

@Serializable
data class GetBarangTerbaruPabrikDistributorDataItem(

    @SerialName("id_master_barang")
    val idMasterBarang: Int,

    @SerialName("nama_rokok")
    val namaRokok: String,

    @SerialName("harga_karton_pabrik")
    val hargaKartonPabrik: Int
)
