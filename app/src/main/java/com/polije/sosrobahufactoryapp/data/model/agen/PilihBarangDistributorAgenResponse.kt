package com.polije.sosrobahufactoryapp.data.model.agen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PilihBarangDistributorAgenResponse(

    @SerialName("distributor")
    val distributor: Distributor,

    @SerialName("barangDistributor")
    val barangDistributor: List<PilihBarangDistributorAgenItem>
)

@Parcelize
@Serializable
data class PilihBarangDistributorAgenItem(

    @SerialName("id_master_barang")
    val idMasterBarang: Int,

    @SerialName("nama_rokok")
    val namaRokok: String,

    @SerialName("harga_karton_pabrik")
    val hargaKartonPabrik: Int,

    @SerialName("stok_karton")
    val stokKarton: Int,

    @SerialName("harga_distributor")
    val hargaDistributor: Int,

    @SerialName("gambar")
    val gambar: String
) : Parcelable

@Serializable
data class Distributor(

    @SerialName("no_rek")
    val noRek: Long,

    @SerialName("nama_lengkap")
    val namaLengkap: String,

    @SerialName("nama_bank")
    val namaBank: String
)
