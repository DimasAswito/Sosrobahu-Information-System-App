package com.polije.sosrobahufactoryapp.ui.agen.order.ubahHarga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.agen.ListBarangPengaturanHargaAgenUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PengaturanHargaAgenViewModel(private val listBarangAgenUseCase: ListBarangPengaturanHargaAgenUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(PengaturanHargaAgenState())

    val state
        get() = _state.onStart {
            initialLoad()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, PengaturanHargaAgenState())

    fun initialLoad() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val data = listBarangAgenUseCase.invoke()
                    when (data) {
                        is DataResult.Error -> _state.update {
                            it.copy(
                                isLoading = false,
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
                                list = data.data.rokokAgens
                            )
                        }
                    }
                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
            }

        }
    }
}