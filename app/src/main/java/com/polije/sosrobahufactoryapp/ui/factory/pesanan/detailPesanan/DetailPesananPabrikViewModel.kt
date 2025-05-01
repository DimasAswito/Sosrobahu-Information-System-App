package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DetailPesananMasukPabrikUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.UpdatePesananPabrikUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class DetailPesananPabrikViewModel(
    private val getDetailPesananMasukPabrikUseCase: DetailPesananMasukPabrikUseCase,
    private val updateDetailPesananMasukUseCase: UpdatePesananPabrikUseCase
) : ViewModel() {
    private val _detailPesanan =
        MutableStateFlow<DetailPesananPabrikState>(DetailPesananPabrikState.Initial)
    val detailPesanan get() = _detailPesanan.asStateFlow()

    private val _updatePesananState = MutableStateFlow<UpdateStatusPesananPabrikState>(
        UpdateStatusPesananPabrikState.Initial
    )
    val updatePesananState get() = _updatePesananState.asStateFlow()


    fun detailPesananMasuk(idOrder: Int) {
        _detailPesanan.value = DetailPesananPabrikState.Loading
        viewModelScope.launch {
            try {
                val data = getDetailPesananMasukPabrikUseCase.invoke(idOrder)
                when (data) {
                    is DataResult.Error -> _detailPesanan.value =
                        DetailPesananPabrikState.Failure(
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

                    is DataResult.Success -> _detailPesanan.value =
                        DetailPesananPabrikState.Success(data.data)
                }
            } catch (e: Exception) {
                _detailPesanan.value =
                    DetailPesananPabrikState.Failure(e.message.toString())
            }
        }
    }

    fun updateDetailPesanan(idOrder: Int, status: Int) {
        _updatePesananState.value = UpdateStatusPesananPabrikState.Loading
        viewModelScope.launch {
            try {
                val data = updateDetailPesananMasukUseCase.invoke(idOrder, status)
                when (data) {
                    is DataResult.Error -> {
                        _updatePesananState.value =
                            UpdateStatusPesananPabrikState.Failure(
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

                    is DataResult.Success -> {
                        delay(2.seconds)
                        _updatePesananState.value = UpdateStatusPesananPabrikState.Success
                    }
                }
            } catch (e: Exception) {
                _detailPesanan.value =
                    DetailPesananPabrikState.Failure(e.message.toString())
            }
        }
    }


}