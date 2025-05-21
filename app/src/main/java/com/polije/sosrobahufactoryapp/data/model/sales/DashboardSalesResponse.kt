package com.polije.sosrobahufactoryapp.data.model.sales

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DashboardSalesResponse(

    @SerialName("daftar_produk_agen")
    val daftarProdukAgen: List<DaftarProdukAgenItem> = emptyList(),

    @SerialName("top_product")
    val topProduct: String? = null,

    @SerialName("nama_sales")
    val namaSales: String? = null,

    @SerialName("total_price")
    val totalPrice: Int? = null,

    @SerialName("jumlah_toko")
    val jumlahToko: Int? = null,

    @SerialName("total_stok")
    val totalStok: Int? = null
)

@Serializable
data class DaftarProdukAgenItem(

    @SerialName("harga_agen")
    val hargaAgen: Int? = null,

    @SerialName("nama_produk")
    val namaProduk: String? = null,

    @SerialName("id_barang_agen")
    val idBarangAgen: Int? = null,

    @SerialName("stok_karton")
    val stokKarton: Int? = null,

    @SerialName("gambar")
    val gambar: String? = null
)
