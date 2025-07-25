package com.polije.sosrobahufactoryapp.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.core.content.ContextCompat
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.distributor.QuantityItem
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.core.net.toUri
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


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

fun uangFormat(value: Long): String {
    val localeID = Locale("id", "ID")
    val symbols = DecimalFormatSymbols(localeID).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val formatter = DecimalFormat("#,###", symbols)
    return formatter.format(value)
}

fun uangUnformat(formatted: String): Long {
    return formatted.replace("[^\\d]".toRegex(), "").toLongOrNull() ?: 0L
}

fun String.toTanggalIndonesiaInstant(): String {
    return try {
        // 1) parse into an Instant
        val instant = Instant.parse(this)
        // 2) convert to system time‐zone
        val zoned   = instant.atZone(ZoneId.systemDefault())
        // 3) format to "dd MMMM yyyy" in Indonesian
        val fmt     = DateTimeFormatter
            .ofPattern("dd MMMM yyyy", Locale("id","ID"))
        zoned.format(fmt)
    } catch (e: Exception) {
        // parsing failed → just return original
        this
    }
}

fun Activity.setStatusBarColorByRole(role: UserRole) {
    val colorRes = when (role) {
        UserRole.PABRIK -> R.color.factory_theme
        UserRole.DISTRIBUTOR -> R.color.distributor_theme
        UserRole.AGEN -> R.color.agen_theme
        UserRole.SALES -> R.color.sales_theme
        UserRole.DEFAULT -> R.color.md_theme_onPrimaryFixedVariant

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
            put("quantities[$i][id_master_barang]", it.idBarang.toString().plain())
            put("quantities[$i][quantity]", it.quantity.toString().plain())
        }
    }
}

fun createOrderPartsAgen(
    totalItems: Int,
    totalAmount: Int,
    quantities: List<QuantityItem>
): Map<String, RequestBody> {
    fun String.plain() = toRequestBody("text/plain".toMediaType())
    return buildMap {
        put("total_items", totalItems.toString().plain())
        put("total_amount", totalAmount.toString().plain())
        quantities.forEachIndexed { i, it ->
            put("quantities[$i][id_barang_distributor]", it.idBarang.toString().plain())
            put("quantities[$i][quantity]", it.quantity.toString().plain())
        }
    }
}

fun createOrderSalesParts(
    totalItems: Int,
    totalAmount: Int,
    quantities: List<QuantityItem>
): Map<String, RequestBody> {
    fun String.plain() = toRequestBody("text/plain".toMediaType())
    return buildMap {
        put("total_items", totalItems.toString().plain())
        put("total_amount", totalAmount.toString().plain())
        quantities.forEachIndexed { i, it ->
            put("quantities[$i][id_barang_agen]", it.idBarang.toString().plain())
            put("quantities[$i][quantity]", it.quantity.toString().plain())
        }
    }
}

fun createKunjunganParts(
    tanggal: String,
    sisaProduk: Int
): Map<String, @JvmSuppressWildcards RequestBody> {
    fun String.plain() = toRequestBody("text/plain".toMediaType())
    return buildMap {
        put("tanggal",     tanggal.plain())
        put("sisa_produk", sisaProduk.toString().plain())
    }
}

fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path =
        MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
    return path.toUri()
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


