package com.polije.sosrobahufactoryapp.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.content.ContextCompat
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.distributor.QuantityItem
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun Int.toRupiah(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatter.format(this)
        .replace(",00", "")
        .replace("Rp", "Rp ")
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

fun Activity.setStatusBarColorByRole(role: UserRole) {
    val colorRes = when (role) {
        UserRole.PABRIK -> R.color.factory_theme
        UserRole.DISTRIBUTOR -> R.color.distributor_theme
        UserRole.AGEN -> R.color.agen_theme
        UserRole.SALES -> R.color.sales_theme
    }

    window.statusBarColor = ContextCompat.getColor(this, colorRes)
}

fun createOrderParts(
    totalItems: Int,
    totalAmount: Int,
    quantities: List<QuantityItem>
): Map<String, RequestBody> {
    fun String.plain() = toRequestBody("text/plain".toMediaType())
    return buildMap {
        put("total_items", totalItems.toString().plain())
        put("total_amount", totalAmount.toString().plain())
        quantities.forEachIndexed { i, it ->
            put("quantities[$i][id_master_barang]", it.id_master_barang.toString().plain())
            put("quantities[$i][quantity]", it.quantity.toString().plain())
        }
    }
}

fun Uri.toMultipartPart(
    context: Context,
    fieldName: String
): MultipartBody.Part {
    val resolver = context.contentResolver
    val input = resolver.openInputStream(this)
        ?: throw IllegalArgumentException("Cannot open URI")
    val bytes = input.readBytes().also { input.close() }
    val mime = resolver.getType(this) ?: "application/octet-stream"
    val body = bytes.toRequestBody(mime.toMediaType())
    val name = resolver.query(this, null, null, null, null)
        ?.use { cursor ->
            val idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(idx)
        } ?: "file"
    return MultipartBody.Part.createFormData(fieldName, name, body)
}


