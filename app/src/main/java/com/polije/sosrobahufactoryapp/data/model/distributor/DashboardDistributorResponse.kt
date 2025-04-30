package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class DashboardDistributorResponse(

	@SerialName("produkData")
	val produkData: List<ProdukDataItem?>? = null,

	@SerialName("topProductName")
	val topProductName: String? = null,

	@SerialName("availableYears")
	val availableYears: List<Int?>? = null,

	@SerialName("finalStockKarton")
	val finalStockKarton: Int? = null,

	@SerialName("totalPendapatan")
	val totalPendapatan: String? = null,

	@SerialName("totalAgen")
	val totalAgen: Int? = null,

	@SerialName("pesananPerBulan")
	val pesananPerBulan: PesananPerBulan? = null
)

@Serializable
data class ProdukDataItem(

	@SerialName("nama_rokok")
	val namaRokok: String? = null,

	@SerialName("gambar")
	val gambar: String? = null,

	@SerialName("total_produk")
	val totalProduk: Int? = null
)

@Serializable
data class PesananPerBulan(

	@SerialName("2025-03")
	val jsonMember202503: JsonMember202503? = null
)

@Serializable
data class PesananItem(

	@SerialName("status_pemesanan")
	val statusPemesanan: Int? = null,

	@SerialName("id_order")
	val idOrder: Int? = null,

	@SerialName("total")
	val total: Int? = null,

	@SerialName("updated_at")
	val updatedAt: String? = null,

	@SerialName("id_user_distributor")
	val idUserDistributor: Int? = null,

	@SerialName("jumlah")
	val jumlah: Int? = null,

	@SerialName("id_user_agen")
	val idUserAgen: Int? = null,

	@SerialName("created_at")
	val createdAt: String? = null,

	@SerialName("bukti_transfer")
	val buktiTransfer: String? = null,

	@SerialName("tanggal")
	val tanggal: String? = null
)

@Serializable
data class JsonMember202503(

	@SerialName("total_omset")
	val totalOmset: Int? = null,

	@SerialName("pesanan")
	val pesanan: List<PesananItem?>? = null,

	@SerialName("total_karton")
	val totalKarton: Int? = null
)
