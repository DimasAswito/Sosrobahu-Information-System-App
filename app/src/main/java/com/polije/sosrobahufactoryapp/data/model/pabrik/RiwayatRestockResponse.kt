package com.polije.sosrobahufactoryapp.data.model.pabrik

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RiwayatRestockResponse(

	@SerialName("per_page")
	val perPage: Int? = null,

	@SerialName("data")
	val data: List<RiwayatRestockItem> = emptyList(),

	@SerialName("last_page")
	val lastPage: Int? = null,

	@SerialName("next_page_url")
	val nextPageUrl: String? = null,

	@SerialName("prev_page_url")
	val prevPageUrl: String? = null,

	@SerialName("first_page_url")
	val firstPageUrl: String? = null,

	@SerialName("path")
	val path: String? = null,

	@SerialName("total")
	val total: Int? = null,

	@SerialName("last_page_url")
	val lastPageUrl: String? = null,

	@SerialName("from")
	val from: Int? = null,

	@SerialName("links")
	val links: List<LinksItem?>? = null,

	@SerialName("to")
	val to: Int? = null,

	@SerialName("current_page")
	val currentPage: Int? = null
)

@Serializable
@Parcelize
data class RiwayatRestockItem(

	@SerialName("jumlah")
	val jumlah: String? = null,

	@SerialName("detail_produk")
	val detailProduk: List<DetailProdukItem?> = emptyList(),

	@SerialName("id_restock")
	val idRestock: Int? = null,

	@SerialName("tanggal")
	val tanggal: String? = null
) : Parcelable

@Serializable
@Parcelize
data class DetailProdukItem(

	@SerialName("id_master_barang")
	val idMasterBarang: Int? = null,

	@SerialName("nama_rokok")
	val namaRokok: String? = null,

	@SerialName("jumlah")
	val jumlah: Int? = null,

	@SerialName("harga_karton_pabrik")
	val hargaKartonPabrik: Int? = null
) : Parcelable

@Serializable
data class LinksItem(

	@SerialName("active")
	val active: Boolean? = null,

	@SerialName("label")
	val label: String? = null,

	@SerialName("url")
	val url: String? = null
)
