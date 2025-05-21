package com.polije.sosrobahufactoryapp.data.model.sales

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RiwayatOrderSalesResponse(

    @SerialName("per_page")
    val perPage: Int? = null,

    @SerialName("data")
    val data: List<OrderSalesDataItem> = emptyList(),

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
    val links: List<OrderSalesLinksItem?>? = null,

    @SerialName("to")
    val to: Int? = null,

    @SerialName("current_page")
    val currentPage: Int? = null
)

@Serializable
data class OrderSalesLinksItem(

    @SerialName("active")
    val active: Boolean? = null,

    @SerialName("label")
    val label: String? = null,

    @SerialName("url")
    val url: String? = null
)

@Parcelize
@Serializable
data class OrderSalesDataItem(

    @SerialName("status_pemesanan")
    val statusPemesanan: Int? = null,

    @SerialName("id_order")
    val idOrder: Int? = null,

    @SerialName("total")
    val total: Int? = null,

    @SerialName("jumlah")
    val jumlah: Int? = null,

    @SerialName("detail_produk")
    val detailProduk: List<OrderSalesDetailProdukItem> = emptyList(),

    @SerialName("bukti_transfer")
    val buktiTransfer: String? = null,

    @SerialName("tanggal")
    val tanggal: String? = null
) : Parcelable

@Parcelize
@Serializable
data class OrderSalesDetailProdukItem(

    @SerialName("id_master_barang")
    val idMasterBarang: Int? = null,

    @SerialName("harga_agen")
    val hargaAgen: Int? = null,

    @SerialName("nama_rokok")
    val namaRokok: String? = null,

    @SerialName("quantity")
    val quantity: Int? = null
) : Parcelable
