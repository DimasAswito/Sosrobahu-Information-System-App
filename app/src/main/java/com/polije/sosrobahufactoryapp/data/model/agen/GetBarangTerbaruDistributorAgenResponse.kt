package com.polije.sosrobahufactoryapp.data.model.agen

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetBarangTerbaruDistributorAgenResponse(

    @SerialName("data")
    val data: List<GetBarangTerbaruDistributorAgenDataItem> = emptyList(),

    @SerialName("success")
    val success: Boolean,

    @SerialName("count")
    val count: Int
)

@Serializable
data class GetBarangTerbaruDistributorAgenDataItem(

    @SerialName("id_master_barang")
    val idMasterBarang: Int,

    @SerialName("nama_rokok")
    val namaRokok: String,

    @SerialName("harga_karton_pabrik")
    val hargaKartonPabrik: Int,

    @SerialName("gambar")
    val gambar: String
)
