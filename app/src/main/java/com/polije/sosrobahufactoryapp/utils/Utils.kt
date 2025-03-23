package com.polije.sosrobahufactoryapp.utils

import java.text.NumberFormat
import java.util.Locale

    fun Int.toRupiah(): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return formatter.format(this).replace(",00", "")
    }