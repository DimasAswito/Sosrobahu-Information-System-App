package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.tambahKunjungan

import android.net.Uri

data class BottomSheetTambahKunjunganState(
    val tanggal: String? = null,
    val sisaProduk: Int? = null,
    val buktiKunjungan: Uri =  Uri.EMPTY,
    val isSubmitted: Boolean = false
)