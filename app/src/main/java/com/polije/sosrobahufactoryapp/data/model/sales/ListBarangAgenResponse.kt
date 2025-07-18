package com.polije.sosrobahufactoryapp.data.model.sales

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListBarangAgenSalesResponse(

    @SerialName("data") val listBarangAgen: List<ListBarangAgenSalesDataItem>,

    @SerialName("success") val success: Boolean,

    @SerialName("distributor") val transfer: ListBarangAgenDistributorInformation
)


@Serializable
@Parcelize
data class ListBarangAgenSalesDataItem(

    @SerialName("id_master_barang") val idMasterBarang: Int,

    @SerialName("nama_rokok") val namaRokok: String,

    @SerialName("id_barang_agen") val idBarangAgen: Int,

    @SerialName("harga") val harga: Int,

    @SerialName("stok") val stok: Int,

    @SerialName("gambar") val gambar: String
) : Parcelable

@Serializable
@Parcelize
data class ListBarangAgenDistributorInformation(

    @SerialName("nama_lengkap") val namaLengkap: String,

    @SerialName("nama_bank") val namaBank: String,
    @SerialName("no_rek") val norek: String

) : Parcelable
