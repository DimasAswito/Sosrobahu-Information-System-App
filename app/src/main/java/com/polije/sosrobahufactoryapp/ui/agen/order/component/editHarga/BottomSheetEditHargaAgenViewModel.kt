package com.polije.sosrobahufactoryapp.ui.agen.order.component.editHarga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.agen.UpdateBarangHargaAgenUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BottomSheetEditHargaAgenViewModel(private val updateHargaBarangAgenUseCase: UpdateBarangHargaAgenUseCase) :
    ViewModel() {

    private val _isSubmitted = MutableStateFlow(false)
    val isSubmitted =
        _isSubmitted.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _harga = MutableStateFlow(0)
    val harga = _harga.asStateFlow()

    val isValid = _harga.map { it > 0 }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun updateHargaField(newValue: Int) {
        _harga.value = newValue
    }


    fun updateHarga(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val data = updateHargaBarangAgenUseCase.invoke(id, _harga.value)
                when (data) {

                    is DataResult.Error -> _errorMessage.value =

                        when (data.error) {
                            HttpErrorCode.BAD_REQUEST -> "Permintaan tidak valid. Periksa kembali listBarangAgen yang dikirimkan."
                            HttpErrorCode.UNAUTHORIZED -> "Login gagal. Username atau password salah."
                            HttpErrorCode.FORBIDDEN -> "Akses ditolak. Anda tidak memiliki izin untuk mengakses."
                            HttpErrorCode.NOT_FOUND -> "Server tidak ditemukan. Coba lagi nanti."
                            HttpErrorCode.TIMEOUT -> "Permintaan melebihi batas waktu. Periksa koneksi internet Anda."
                            HttpErrorCode.INTERNAL_SERVER_ERROR -> "Terjadi kesalahan pada server. Silakan coba beberapa saat lagi."
                            HttpErrorCode.UNKNOWN -> "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi."


                        }

                    is DataResult.Success -> _isSubmitted.value = true

                }
            }
        }
    }
}