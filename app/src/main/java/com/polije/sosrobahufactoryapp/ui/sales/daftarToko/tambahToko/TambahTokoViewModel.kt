package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.tambahToko

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.sales.TambahTokoUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TambahTokoViewModel(private val insertTokoUseCase: TambahTokoUseCase) : ViewModel() {
    private val _state = MutableStateFlow(TambahTokoState())
    val state get() = _state.asStateFlow()

    val isValid = _state.map { state ->
        state.isValid
    }

    fun onNamaTokoChange(value: String) {
        _state.update { it.copy(namaToko = value) }
    }

    fun onNamaPemilikChange(value: String) {
        _state.update { it.copy(namaPemilik = value) }
    }

    fun onNoTelpChanged(value: String) {
        _state.update { it.copy(noTelp = value) }
    }

    fun onLokasiChanged(value: String) {
        _state.update { it.copy(lokasi = value) }
    }

    fun insertToko() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {

            val data = insertTokoUseCase.invoke(
                _state.value.namaToko,
                _state.value.namaPemilik,
                _state.value.noTelp,
                _state.value.lokasi
            )

            when (data) {
                is DataResult.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        isSubmitted = false,
                        errorMessage = when (data.error) {
                            HttpErrorCode.BAD_REQUEST -> "Permintaan tidak valid. Periksa kembali listBarangAgen yang dikirimkan."
                            HttpErrorCode.UNAUTHORIZED -> "Login gagal. Username atau password salah."
                            HttpErrorCode.FORBIDDEN -> "Akses ditolak. Anda tidak memiliki izin untuk mengakses."
                            HttpErrorCode.NOT_FOUND -> "Server tidak ditemukan. Coba lagi nanti."
                            HttpErrorCode.TIMEOUT -> "Permintaan melebihi batas waktu. Periksa koneksi internet Anda."
                            HttpErrorCode.INTERNAL_SERVER_ERROR -> "Terjadi kesalahan pada server. Silakan coba beberapa saat lagi."
                            HttpErrorCode.UNKNOWN -> "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi."
                        }
                    )
                }

                is DataResult.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        isSubmitted = true
                    )
                }
            }
        }
    }
}
