package com.polije.sosrobahufactoryapp.data.model.agen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PesananMasukAgenResponse(

	@SerialName("per_page")
	val perPage: Int? = null,

	@SerialName("listBarangAgen")
	val data: List<PesananMasukAgenDataItem> = emptyList(),

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
	val links: List<PesananMasukAgenLinksItem> = emptyList(),

	@SerialName("to")
	val to: Int? = null,

	@SerialName("current_page")
	val currentPage: Int? = null
)

@Serializable
@Parcelize
data class PesananMasukAgenDataItem(

	@SerialName("status_pemesanan")
	val statusPemesanan: Int? = null,

	@SerialName("id_order")
	val idOrder: Int? = null,

	@SerialName("total")
	val total: Int? = null,

	@SerialName("nama_sales")
	val namaSales: String? = null,

	@SerialName("updated_at")
	val updatedAt: String? = null,

	@SerialName("jumlah")
	val jumlah: Int? = null,

	@SerialName("id_user_sales")
	val idUserSales: Int? = null,

	@SerialName("id_user_agen")
	val idUserAgen: Int? = null,

	@SerialName("created_at")
	val createdAt: String? = null,

	@SerialName("bukti_transfer")
	val buktiTransfer: String? = null,

	@SerialName("tanggal")
	val tanggal: String? = null
) : Parcelable

@Serializable
data class PesananMasukAgenLinksItem(

	@SerialName("active")
	val active: Boolean? = null,

	@SerialName("label")
	val label: String? = null,

	@SerialName("url")
	val url: String? = null
)
