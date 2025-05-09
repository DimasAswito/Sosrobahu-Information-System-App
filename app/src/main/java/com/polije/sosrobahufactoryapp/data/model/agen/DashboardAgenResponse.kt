package com.polije.sosrobahufactoryapp.data.model.agen

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class DashboardAgenResponse(

	@SerialName("top_product")
	val topProduct: String? = null,

	@SerialName("total_pendapatan")
	val totalPendapatan: Int? = null,

	@SerialName("success")
	val success: Boolean? = null,

	@SerialName("total_sales")
	val totalSales: Int? = null,

	@SerialName("stok_barang")
	val stokBarang: List<StokBarangAgenItem> = emptyList(),

	@SerialName("total_stok_keseluruhan")
	val totalStokKeseluruhan: Int? = null


)

@Serializable
data class StokBarangAgenItem(

	@SerialName("nama_rokok")
	val namaRokok: String? = null,

	@SerialName("stok")
	val stok: Int? = null,

	@SerialName("gambar")
	val gambar: String? = null
)
