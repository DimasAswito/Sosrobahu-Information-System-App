package com.polije.sosrobahufactoryapp.ui.distributor.order.detailOrder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.DetailPesananMasukUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailOrderDistributorViewModel(private val detailPesananMasukUseCase: DetailPesananMasukUseCase) :
    ViewModel() {
    private val _state =
        MutableStateFlow<DetailOrderDistributorState>(DetailOrderDistributorState())
    val state get() = _state.asStateFlow()

    fun detailPesananMasuk(idOrder: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val data = detailPesananMasukUseCase.invoke(idOrder)
                when (data) {
                    is DataResult.Error -> _state.update {
                        it.copy(
                            error =
                                when (data.error) {
                                    HttpErrorCode.BAD_REQUEST -> "Permintaan tidak valid. Periksa kembali data yang dikirimkan."
                                    HttpErrorCode.UNAUTHORIZED -> "Login gagal. Username atau password salah."
                                    HttpErrorCode.FORBIDDEN -> "Akses ditolak. Anda tidak memiliki izin untuk mengakses."
                                    HttpErrorCode.NOT_FOUND -> "Server tidak ditemukan. Coba lagi nanti."
                                    HttpErrorCode.TIMEOUT -> "Permintaan melebihi batas waktu. Periksa koneksi internet Anda."
                                    HttpErrorCode.INTERNAL_SERVER_ERROR -> "Terjadi kesalahan pada server. Silakan coba beberapa saat lagi."
                                    HttpErrorCode.UNKNOWN -> "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi."
                                }

                        )
                    }

                    is DataResult.Success -> _state.update { it.copy(data = data.data) }

                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }

            }
        }
    }
}