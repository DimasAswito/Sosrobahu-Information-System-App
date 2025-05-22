package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.detailDaftarToko

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.sales.DeleteKunjunganTokoUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.DeleteTokoUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.sales.UpdateTokoUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailDaftarTokoSalesViewModel(
    private val updateTokoUseCase: UpdateTokoUseCase,
    private val deleteTokoUseCase: DeleteTokoUseCase,
    private val deleteKunjunganTokoUseCase: DeleteKunjunganTokoUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DetailDaftarTokoSalesState())
    val state = _state.asStateFlow()

    fun updateToko(
        idToko: Int,
        namaToko: String,
        namaPemilik: String,
        noTelp: String,
        lokasi: String
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response =
                    updateTokoUseCase.invoke(idToko, namaToko, namaPemilik, noTelp, lokasi)
                when (response) {
                    is DataResult.Error -> _state.update {
                        it.copy(
                            isUpdated = false,
                            errorMessage = when (response.error) {
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
                            isUpdated = true
                        )
                    }
                }
            }
        }
    }

    fun deleteToko(
        idToko: Int,
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = deleteTokoUseCase.invoke(idToko)
                when (response) {
                    is DataResult.Error -> _state.update {
                        it.copy(
                            isUpdated = false,
                            errorMessage = when (response.error) {
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
                            isUpdated = true
                        )
                    }
                }

            }
        }
    }

    fun deleteKunjungan(idKunjungan: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = deleteKunjunganTokoUseCase.invoke(idKunjungan)
                when (response) {
                    is DataResult.Error -> _state.update {
                        it.copy(
                            isUpdated = false,
                            errorMessage = when (response.error) {
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
                            isUpdated = true
                        )
                    }
                }

            }
        }
    }


}