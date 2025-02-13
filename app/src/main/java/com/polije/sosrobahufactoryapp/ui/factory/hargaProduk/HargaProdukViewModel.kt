package com.polije.sosrobahufactoryapp.ui.factory.hargaProduk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HargaProdukViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Harga Produk Fragment"
    }
    val text: LiveData<String> = _text
}