package com.polije.sosrobahufactoryapp.model

import java.sql.Timestamp

data class order_detail_distributor (
    val id_detail_distributor: Int,
    val id_order: Int,
    val id_user_pabrik: Int,
    val id_user_distributor: Int,
    val id_master_barang: Int,
    val jumlah_produk: Int,
    val jumlah_harga_item: Int,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)