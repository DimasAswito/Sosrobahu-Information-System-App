package com.polije.sosrobahufactoryapp.data.model.agen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RiwayatOrderAgenResponse(

	@SerialName("per_page")
	val perPage: Int? = null,

	@SerialName("listBarangAgen")
	val data: List<RiwayatOrderAgenDataItem> = emptyList(),

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
	val links: List<RiwayatOrderAgenLinksItem?>? = null,

	@SerialName("to")
	val to: Int? = null,

	@SerialName("current_page")
	val currentPage: Int? = null
)
@Parcelize
@Serializable
data class RiwayatOrderAgenDetailProdukItem(

	@SerialName("id_master_barang")
	val idMasterBarang: Int? = null,

	@SerialName("nama_rokok")
	val namaRokok: String? = null,

	@SerialName("quantity")
	val quantity: Int? = null,

	@SerialName("harga_karton_pabrik")
	val hargaKartonPabrik: Int? = null
) : Parcelable

@Parcelize
@Serializable
data class RiwayatOrderAgenDataItem(

	@SerialName("status_pemesanan")
	val statusPemesanan: Int? = null,

	@SerialName("id_order")
	val idOrder: Int? = null,

	@SerialName("total")
	val total: Int? = null,

	@SerialName("jumlah")
	val jumlah: Int? = null,

	@SerialName("detail_produk")
	val detailProduk: List<RiwayatOrderAgenDetailProdukItem> = emptyList(),

	@SerialName("bukti_transfer")
	val buktiTransfer: String? = null,

	@SerialName("tanggal")
	val tanggal: String? = null
) : Parcelable

@Serializable
data class RiwayatOrderAgenLinksItem(

	@SerialName("active")
	val active: Boolean? = null,

	@SerialName("label")
	val label: String? = null,

	@SerialName("url")
	val url: String? = null
)
