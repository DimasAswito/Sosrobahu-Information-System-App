package com.polije.sosrobahufactoryapp.model

import androidx.annotation.DrawableRes

data class RiwayatRestok(
    val namaProduk: String,
    val tanggalRestok: String,
    val jumlahProduk: Int,
    @DrawableRes
    val gambar: Int
)
