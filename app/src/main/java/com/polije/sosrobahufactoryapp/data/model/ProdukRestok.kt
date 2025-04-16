package com.polije.sosrobahufactoryapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ProdukRestok(

	@SerialName("barangPabriks")
		val barangPabriks: List<ProdukRestokItem>
)

@Parcelize
@Serializable
data class ProdukRestokItem(

	@SerialName("id_master_barang")
	val idMasterBarang: Int,

	@SerialName("nama_rokok")
	val namaRokok: String,

	@SerialName("updated_at")
	val updatedAt: String,

	@SerialName("harga_karton_pabrik")
	val hargaKartonPabrik: Int,

	@SerialName("created_at")
	val createdAt: String,

	@SerialName("gambar")
	val gambar: String,

	@SerialName("stok_slop")
	val stokSlop: Int
): Parcelable
