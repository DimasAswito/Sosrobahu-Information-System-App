package com.polije.sosrobahufactoryapp.data.model

import java.sql.Timestamp

data class restock_pabrik(
    val id_restock: Int,
    val id_user_pabrik: Int,
    val jumlah: Int,
    val tanggal: Timestamp,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)
