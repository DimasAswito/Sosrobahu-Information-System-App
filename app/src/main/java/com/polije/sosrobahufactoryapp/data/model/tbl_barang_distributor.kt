package com.polije.sosrobahufactoryapp.data.model

import java.sql.Timestamp

data class tbl_barang_distributor (
    val id_barang_distributor: Int,
    val id_master_barang: Int,
    val id_user_distributor: Int,
    val harga_distributor: Int,
    val stok_karton: Int,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)