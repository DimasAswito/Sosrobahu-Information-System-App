package com.polije.sosrobahufactoryapp.ui.distributor.pesanan.detailPesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.DetailPesananMasukDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.UpdateStatusPesananDistributorUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailPesananDistributorViewModel(
    private val detailPesananMasukDistributorUseCase: DetailPesananMasukDistributorUseCase,
    private val updateDetailPesananDistributorUseCase: UpdateStatusPesananDistributorUseCase
) :
    ViewModel() {
    private val _state = MutableStateFlow(DetailPesananDistributorState())
    val state get() = _state.asStateFlow()

    fun getDetailPesananMasuk(idOrder: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val data = detailPesananMasukDistributorUseCase.invoke(idOrder)
            when (data) {
                is DataResult.Error -> {
                    _state.update {
                        it.copy(
                            errorMessage = when (data.error) {
                                HttpErrorCode.BAD_REQUEST -> "Permintaan tidak valid. Periksa kembali data yang dikirimkan."
                                HttpErrorCode.UNAUTHORIZED -> "Login gagal. Username atau password salah."
                                HttpErrorCode.FORBIDDEN -> "Akses ditolak. Anda tidak memiliki izin untuk mengakses."
                                HttpErrorCode.NOT_FOUND -> "Server tidak ditemukan. Coba lagi nanti."
                                HttpErrorCode.TIMEOUT -> "Permintaan melebihi batas waktu. Periksa koneksi internet Anda."
                                HttpErrorCode.INTERNAL_SERVER_ERROR -> "Terjadi kesalahan pada server. Silakan coba beberapa saat lagi."
                                HttpErrorCode.UNKNOWN -> "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi."
                            }, isLoading = false
                        )
                    }
                }

                is DataResult.Success -> _state.update {
                    it.copy(
                        data = data.data,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateStatusPesananMasuk(idOrder: Int, status: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val data = updateDetailPesananDistributorUseCase.invoke(idOrder, status)
            when (data) {
                is DataResult.Error -> {
                    _state.update {
                        it.copy(
                            errorMessage = when (data.error) {
                                HttpErrorCode.BAD_REQUEST -> "Permintaan tidak valid. Periksa kembali data yang dikirimkan."
                                HttpErrorCode.UNAUTHORIZED -> "Login gagal. Username atau password salah."
                                HttpErrorCode.FORBIDDEN -> "Akses ditolak. Anda tidak memiliki izin untuk mengakses."
                                HttpErrorCode.NOT_FOUND -> "Server tidak ditemukan. Coba lagi nanti."
                                HttpErrorCode.TIMEOUT -> "Permintaan melebihi batas waktu. Periksa koneksi internet Anda."
                                HttpErrorCode.INTERNAL_SERVER_ERROR -> "Terjadi kesalahan pada server. Silakan coba beberapa saat lagi."
                                HttpErrorCode.UNKNOWN -> "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi."
                            }, isLoading = false
                        )
                    }
                }

                is DataResult.Success -> _state.update {
                    it.copy(
                        isSubmitted = true,
                        isLoading = false
                    )
                }
            }
        }
    }
}
