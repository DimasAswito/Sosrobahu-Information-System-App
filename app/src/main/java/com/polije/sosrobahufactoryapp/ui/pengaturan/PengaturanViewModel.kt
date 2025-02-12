package com.polije.sosrobahufactoryapp.ui.pengaturan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PengaturanViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Pengaturan Fragment"
    }
    val text: LiveData<String> = _text
}