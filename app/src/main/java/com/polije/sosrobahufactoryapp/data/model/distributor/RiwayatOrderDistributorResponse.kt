package com.polije.sosrobahufactoryapp.data.model.distributor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RiwayatOrderDistributorResponse(

    @SerialName("per_page")
    val perPage: Int? = null,

    @SerialName("data")
    val data: List<RiwayatOrderDistributorDataItem?> = emptyList(),

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
    val links: List<RiwayatOrderDistributorLinkItems?> = emptyList(),

    @SerialName("to")
    val to: Int? = null,

    @SerialName("current_page")
    val currentPage: Int? = null
)

@Serializable
data class RiwayatOrderDistributorLinkItems(

    @SerialName("active")
    val active: Boolean? = null,

    @SerialName("label")
    val label: String? = null,

    @SerialName("url")
    val url: String? = null
)

@Serializable
data class RiwayatOrderDistributorDataItem(

    @SerialName("status_pemesanan")
    val statusPemesanan: Int? = null,

    @SerialName("id_order")
    val idOrder: Int? = null,

    @SerialName("total")
    val total: Int? = null,

    @SerialName("jumlah")
    val jumlah: Int? = null,

    @SerialName("bukti_transfer")
    val buktiTransfer: String? = null,

    @SerialName("tanggal")
    val tanggal: String? = null
)
