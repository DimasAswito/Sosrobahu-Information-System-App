package com.polije.sosrobahufactoryapp.data.model.sales

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class KunjunganTokoResponse(

	@SerialName("gambarTokoList")
	val gambarTokoList: List<String?> = emptyList(),

	@SerialName("kunjunganToko")
	val kunjunganToko: KunjunganToko,

	@SerialName("id_toko")
	val idToko: String? = null,

	@SerialName("storeName")
	val storeName: String? = null
)

@Serializable
data class KunjunganToko(

	@SerialName("per_page")
	val perPage: Int? = null,

	@SerialName("data")
	val data: List<KunjunganTokoDataItem> = emptyList(),

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
	val links: List<KunjunganTokoLinksItem?>? = null,

	@SerialName("to")
	val to: Int? = null,

	@SerialName("current_page")
	val currentPage: Int? = null
)

@Serializable
data class KunjunganTokoLinksItem(

	@SerialName("active")
	val active: Boolean? = null,

	@SerialName("label")
	val label: String? = null,

	@SerialName("url")
	val url: String? = null
)

@Serializable
data class KunjunganTokoDataItem(

	@SerialName("id_daftar_toko")
	val idDaftarToko: Int? = null,

	@SerialName("updated_at")
	val updatedAt: String? = null,

	@SerialName("id_user_sales")
	val idUserSales: Int? = null,

	@SerialName("created_at")
	val createdAt: String? = null,

	@SerialName("tanggal")
	val tanggal: String? = null,

	@SerialName("sisa_produk")
	val sisaProduk: Int? = null,

	@SerialName("gambar")
	val gambar: String? = null,

	@SerialName("id_kunjungan_toko")
	val idKunjunganToko: Int? = null
)
