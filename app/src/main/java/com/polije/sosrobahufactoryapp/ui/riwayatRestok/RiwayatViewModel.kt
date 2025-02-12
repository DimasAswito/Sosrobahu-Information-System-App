package com.polije.sosrobahufactoryapp.ui.riwayatRestok

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RiwayatViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is riwayat restok Fragment"
    }
    val text: LiveData<String> = _text
}