package com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.data.model.agen.PilihBarangDistributorAgenItem
import com.polije.sosrobahufactoryapp.domain.usecase.agen.PilihBarangDistributorAgenUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class PilihProdukAgenViewModel(private val pilihProdukDistributorAgenUseCase: PilihBarangDistributorAgenUseCase) : ViewModel(){
    private val _state = MutableStateFlow(PilihProdukDistributorAgenState())
    val state: StateFlow<PilihProdukDistributorAgenState>
        get() = _state.onStart {
            getListPoduk()
        }.stateIn(viewModelScope, SharingStarted.Lazily, PilihProdukDistributorAgenState())

    private val _selectedProducts = MutableStateFlow<List<SelectedProdukAgen>>(emptyList())
    val selectedProducts: StateFlow<List<SelectedProdukAgen>> = _selectedProducts

    private fun getListPoduk() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val data = pilihProdukDistributorAgenUseCase.invoke()
            when (data) {
                is DataResult.Error -> _state.update {
                    it.copy(
                        errorMessage = when (data.error) {
                            HttpErrorCode.BAD_REQUEST -> "Permintaan tidak valid. Periksa kembali listBarangAgen yang dikirimkan."
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

    fun toggleProdukSelection(item: PilihBarangDistributorAgenItem) {
        val current = _selectedProducts.value.toMutableList()
        val existing = current.find { it.item.idBarangDistributor == item.idBarangDistributor }

        if (existing != null) {
            current.remove(existing)
        } else {
            current.add(SelectedProdukAgen(item = item))
        }

        _selectedProducts.value = current
    }


}

@Parcelize
data class SelectedProdukAgen(
    val item: PilihBarangDistributorAgenItem,
    var quantity: Int? = null,
    var hasFocus: Boolean = false,
    var cursorPosition: Int = -1
) : Parcelable