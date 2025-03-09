package com.polije.sosrobahufactoryapp.model

import java.sql.Timestamp

data class restock_detail_pabrik (
    val id_detail_pabrik: Int,
    val id_restock: Int,
    val id_user_pabrik: Int,
    val id_master_barang: Int,
    val jumlah_produk: Int,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)