package com.polije.sosrobahufactoryapp.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun Int.toRupiah(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatter.format(this).replace(",00", "")
}

fun String.toTanggalIndonesia(): String {
    val localeID = Locale("id", "ID")

    val possibleFormats = listOf(
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault()),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()),
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()),
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    )

    for (format in possibleFormats) {
        try {
            val date = format.parse(this)
            date?.let {
                val outputFormat = SimpleDateFormat("dd MMMM yyyy", localeID)
                return outputFormat.format(it)
            }
        } catch (_: Exception) {
        }
    }

    return this
}
