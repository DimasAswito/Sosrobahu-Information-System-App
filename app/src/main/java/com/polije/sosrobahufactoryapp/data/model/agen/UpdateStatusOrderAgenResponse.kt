package com.polije.sosrobahufactoryapp.data.model.agen

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UpdateStatusOrderAgenResponse(

	@SerialName("data")
	val data: UpdateStatusOrderAgenData? = null,

	@SerialName("success")
	val success: Boolean? = null,

	@SerialName("message")
	val message: String? = null
)

@Serializable
data class UpdateStatusOrderAgenData(

	@SerialName("status_pemesanan")
	val statusPemesanan: Int? = null,

	@SerialName("id_order")
	val idOrder: Int? = null,

	@SerialName("total")
	val total: Int? = null,

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
)
