package com.polije.sosrobahufactoryapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProdukRestok(
    val namaProduk: String,
    val gambar: Int, // Drawable resource ID
    var jumlah: Int = 0, // Default jumlah produk 0
    var tanggal: String = "" // Default tanggal kosong
) : Parcelable
