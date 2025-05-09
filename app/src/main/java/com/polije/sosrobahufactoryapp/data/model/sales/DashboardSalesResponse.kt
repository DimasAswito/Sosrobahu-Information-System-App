package com.polije.sosrobahufactoryapp.data.model.sales

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class DashboardSalesResponse(

	@SerialName("top_product")
	val topProduct: String,

	@SerialName("total_price")
	val totalPrice: Int,

	@SerialName("jumlah_toko")
	val jumlahToko: Int,

	@SerialName("total_stok")
	val totalStok: Int
)
