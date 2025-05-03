package com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.PilihProdukPabrikDistributorUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PilihProdukDistributorViewModel(private val pilihProdukPabrikDistributorUseCase: PilihProdukPabrikDistributorUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(PilihProdukDistributorState())
    val state: StateFlow<PilihProdukDistributorState>
        get() = _state.onStart {
            getListPoduk()
        }.stateIn(viewModelScope, SharingStarted.Lazily, PilihProdukDistributorState())

    private fun getListPoduk() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val data = pilihProdukPabrikDistributorUseCase.invoke()
            when (data) {
                is DataResult.Error -> _state.update {
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

                is DataResult.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        data = data.data
                    )
                }
            }
        }
    }


}