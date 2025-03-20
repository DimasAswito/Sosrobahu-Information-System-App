package com.polije.sosrobahufactoryapp.model

data class DashboardPabrikResponse(
    val finalStockKarton: Int,
    val totalPendapatan: Int,
    val totalDistributor: Int,
    val pesananPerbulan: Map<String, PesananPerBulan>,
    val topProductName: String,  // Tambahkan ini
    val namaRokokList: List<String>,  // Tambahkan ini
    val gambarRokokList: List<String>,  // Tambahkan ini
    val totalProdukList: List<Int>  // Tambahkan ini
)


data class PesananPerBulan(
    val total_omset: Int
)

data class TopProduct(
    val name: String,
    val image: String,
    val stock: Int
)
