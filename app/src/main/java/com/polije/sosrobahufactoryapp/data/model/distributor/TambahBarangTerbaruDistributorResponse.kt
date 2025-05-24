package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TambahBarangTerbaruDistributorResponse(

    @SerialName("data")
    val data: List<TambahBarangTerbaruDistributorDataItem>,

    @SerialName("message")
    val message: String,

    @SerialName("added_count")
    val addedCount: Int
)

@Serializable
data class TambahBarangTerbaruDistributorDataItem(

    @SerialName("id_master_barang")
    val idMasterBarang: Int,

    @SerialName("id_user_distributor")
    val idUserDistributor: Int,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("stok_karton")
    val stokKarton: Int,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("harga_distributor")
    val hargaDistributor: Int
)
