package com.polije.sosrobahufactoryapp.data.model.sales

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListTokoSalesResponse(

    @SerialName("stores")
    val stores: ListTokoSalesStores,

    @SerialName("message")
    val message: String? = null
)

@Serializable
data class ListTokoSalesDataItem(

    @SerialName("id_daftar_toko")
    val idDaftarToko: Int? = null,

    @SerialName("nama_pemilik")
    val namaPemilik: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null,

    @SerialName("id_user_sales")
    val idUserSales: Int? = null,

    @SerialName("lokasi")
    val lokasi: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("no_telp")
    val noTelp: String? = null,

    @SerialName("nama_toko")
    val namaToko: String? = null
)

@Serializable
data class ListTokoSalesLinksItem(

    @SerialName("active")
    val active: Boolean? = null,

    @SerialName("label")
    val label: String? = null,

    @SerialName("url")
    val url: String? = null
)

@Serializable
data class ListTokoSalesStores(

    @SerialName("per_page")
    val perPage: Int? = null,

    @SerialName("data")
    val data: List<ListTokoSalesDataItem> = emptyList(),

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
    val links: List<ListTokoSalesLinksItem> = emptyList(),

    @SerialName("to")
    val to: Int? = null,

    @SerialName("current_page")
    val currentPage: Int? = null
)
