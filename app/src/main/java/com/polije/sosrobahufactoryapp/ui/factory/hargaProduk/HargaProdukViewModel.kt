package com.polije.sosrobahufactoryapp.ui.factory.hargaProduk

import androidx.lifecycle.ViewModel
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.model.Produk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HargaProdukViewModel : ViewModel() {
    private var _produkList = MutableStateFlow<List<Produk>>(emptyList())
    val produkList get() = _produkList.asStateFlow()

    init {
        _produkList.update {
            mutableListOf(
            Produk("Produk A", "Rp 10.000", R.drawable.logo),
            Produk("Produk B", "Rp 20.000", R.drawable.logo),
            Produk("Produk C", "Rp 15.000", R.drawable.logo),
            Produk("Produk D", "Rp 30.000", R.drawable.logo)
        )
        }
    }

}