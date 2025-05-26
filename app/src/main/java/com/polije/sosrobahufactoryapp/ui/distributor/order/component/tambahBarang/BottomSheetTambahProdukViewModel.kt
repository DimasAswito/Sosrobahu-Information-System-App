package com.polije.sosrobahufactoryapp.ui.distributor.order.component.tambahBarang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.data.model.distributor.GetBarangTerbaruPabrikDistributorDataItem
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.BarangTerbaruPabrikDistributorUseCase
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.UploadNewBarangDistributorUseCase
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.SelectedNewProdukDistributor
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BottomSheetTambahProdukViewModel(
    private val barangTerbaruPabrikDistributorUseCase: BarangTerbaruPabrikDistributorUseCase,
    private val uploadNewBarangUseCase: UploadNewBarangDistributorUseCase
) :
    ViewModel() {

    private val _state = MutableStateFlow(BottomSheetTambahProdukState())
    val state = _state.onStart {
        fetchBarangTerbaru()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, BottomSheetTambahProdukState())

    private val _selectedProducts =
        MutableStateFlow<List<SelectedNewProdukDistributor>>(emptyList())
    val selectedProducts: StateFlow<List<SelectedNewProdukDistributor>> = _selectedProducts

    private fun fetchBarangTerbaru() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val data = barangTerbaruPabrikDistributorUseCase.invoke()
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
                                listBarang = data.data.data
                            )
                        }
                    }
                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
            }
        }
    }

    fun toggleProdukSelection(item: GetBarangTerbaruPabrikDistributorDataItem) {
        val current = _selectedProducts.value.toMutableList()
        val existing = current.find { it.item.idMasterBarang == item.idMasterBarang }

        if (existing != null) {
            current.remove(existing)
        } else {
            current.add(SelectedNewProdukDistributor(item = item))
        }

        _selectedProducts.value = current
    }

    fun insertProdukTerbaru(ids: List<Int>) {
        viewModelScope.launch {
            try {
                val data = uploadNewBarangUseCase.invoke(ids)
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
                            isSubmitted = true
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}

