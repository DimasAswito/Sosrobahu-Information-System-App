package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.data.model.pabrik.ProdukRestokItem
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.GetItemRestockPabrikUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlin.time.Duration.Companion.seconds

class ProdukRestokPabrikViewModel(private val getItemRestockPabrikUseCase: GetItemRestockPabrikUseCase) :
    ViewModel() {

    private val _productsState =
        MutableStateFlow<PilihProdukRestockPabrikState>(PilihProdukRestockPabrikState.Loading)
    val productsState: StateFlow<PilihProdukRestockPabrikState> = _productsState.onStart {
        fetchProdukRestok()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        PilihProdukRestockPabrikState.Loading
    )

    private val _selectedProducts = MutableStateFlow<List<SelectedProdukRestokPabrik>>(emptyList())
    val selectedProducts: StateFlow<List<SelectedProdukRestokPabrik>> = _selectedProducts

    fun fetchProdukRestok() {
        viewModelScope.launch {
            _productsState.value = PilihProdukRestockPabrikState.Loading
            try {
                val result = getItemRestockPabrikUseCase.invoke()
                when (result) {
                    is DataResult.Error -> _productsState.value =
                        PilihProdukRestockPabrikState.Error(
                            when (result.error) {
                                HttpErrorCode.BAD_REQUEST -> "Permintaan tidak valid. Periksa kembali listBarangAgen yang dikirimkan."
                                HttpErrorCode.UNAUTHORIZED -> "Login gagal. Username atau password salah."
                                HttpErrorCode.FORBIDDEN -> "Akses ditolak. Anda tidak memiliki izin untuk mengakses."
                                HttpErrorCode.NOT_FOUND -> "Server tidak ditemukan. Coba lagi nanti."
                                HttpErrorCode.TIMEOUT -> "Permintaan melebihi batas waktu. Periksa koneksi internet Anda."
                                HttpErrorCode.INTERNAL_SERVER_ERROR -> "Terjadi kesalahan pada server. Silakan coba beberapa saat lagi."
                                HttpErrorCode.UNKNOWN -> "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi."
                            }
                        )

                    is DataResult.Success -> _productsState.value =
                        PilihProdukRestockPabrikState.Success(result.data)
                }
            } catch (e: Exception) {
                _productsState.value =
                    PilihProdukRestockPabrikState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    fun toggleProdukSelection(item: ProdukRestokItem) {
        val current = _selectedProducts.value.toMutableList()
        val existing = current.find { it.item.idMasterBarang == item.idMasterBarang }

        if (existing != null) {
            current.remove(existing)
        } else {
            current.add(SelectedProdukRestokPabrik(item = item))
        }

        _selectedProducts.value = current
    }
}

@Parcelize
data class SelectedProdukRestokPabrik(
    val item: ProdukRestokItem,
    var quantity: Int = 0,
    var hasFocus: Boolean = false,
    var cursorPosition: Int = -1
) : Parcelable