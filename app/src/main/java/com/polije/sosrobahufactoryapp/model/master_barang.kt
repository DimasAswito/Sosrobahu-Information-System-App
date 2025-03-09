package com.polije.sosrobahufactoryapp.model

import java.sql.Timestamp

data class master_barang(
    val id_master_barang: Int,
    val nama_rokok: Char,
    val harga_Karton_pabrik: Int,
    val gambar: Char,
    val stok_slop: Int,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)

