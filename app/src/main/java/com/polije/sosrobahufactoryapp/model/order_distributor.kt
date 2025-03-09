package com.polije.sosrobahufactoryapp.model

import java.sql.Timestamp

data class order_distributor (
    val id_order: Int,
    val id_user_distributor: Int,
    val jumlah: Int,
    val total: Int,
    val tanggal: Timestamp,
    val bukti_transfer:Char,
    val status_pemesanan:Int,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)