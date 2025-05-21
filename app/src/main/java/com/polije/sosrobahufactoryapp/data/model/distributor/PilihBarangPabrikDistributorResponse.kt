package com.polije.sosrobahufactoryapp.data.model.distributor

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@JsonIgnoreUnknownKeys
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
    val gambar: String? = null,

    @SerialName("harga_karton_pabrik")
    val hargaKartonPabrik : Int,

    @SerialName("stok_slop")
    val stokSlop : Int? = null,

    @SerialName("created_at")
    val createdAt : String? = null,

    @SerialName("updated_at")
    val updateAt : String? = null
) : Parcelable

@Serializable
data class BankPabrikResponse(
    @SerialName("nama_lengkap") val namaLengkap: String? = null,
    @SerialName("nama_bank")
    val namaBank: String? = null,

    @SerialName("no_rek")
    val norek: Int? = null
)
