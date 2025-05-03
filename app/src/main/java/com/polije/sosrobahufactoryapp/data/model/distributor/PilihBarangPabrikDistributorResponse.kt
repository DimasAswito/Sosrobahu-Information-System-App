package com.polije.sosrobahufactoryapp.data.model.distributor

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PilihBarangPabrikDistributorResponse(

    @SerialName("barangPabriks")
    val pilihBarangPabrikDistributorResponse: List<PilihBarangPabrikDistributorResponseItem> = emptyList(),
    @SerialName("pabrik")
    val bankPabrikResponse: BankPabrikResponse? = null
)

@Serializable
@Parcelize
data class PilihBarangPabrikDistributorResponseItem(

    @SerialName("id_master_barang")
    val idMasterBarang: Int? = null,

    @SerialName("nama_rokok")
    val namaRokok: String? = null,

    @SerialName("gambar")
    val gambar: String? = null
) : Parcelable

@Serializable
data class BankPabrikResponse(
    @SerialName("nama_lengkap") val namaLengkap: String? = null,
    @SerialName("nama_bank")
    val namaBank: String? = null,

    @SerialName("no_rek")
    val norek: Int? = null
)
