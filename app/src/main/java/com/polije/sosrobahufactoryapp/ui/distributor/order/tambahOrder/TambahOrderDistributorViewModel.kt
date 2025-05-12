package com.polije.sosrobahufactoryapp.ui.distributor.order.tambahOrder

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.OrderDistributorUseCase
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TambahOrderDistributorViewModel(val distributorOrderUseCase: OrderDistributorUseCase) :
    ViewModel() {
    private val _produkRestock = MutableStateFlow<List<SelectedProdukDistributor>>(emptyList())
    val produkRestock: StateFlow<List<SelectedProdukDistributor>> get() = _produkRestock.asStateFlow()

    private val _buktiTransfer = MutableStateFlow<Uri>(Uri.EMPTY)
    val buktiTransfer: StateFlow<Uri> get() = _buktiTransfer.asStateFlow()

    private val _tambahOrderDistributorState = MutableStateFlow(TambahOrderDistributorState())
    val tambahOrderDistributorState get() = _tambahOrderDistributorState.asStateFlow()


    fun initialProdukRestock(orders: List<SelectedProdukDistributor>) {
        _produkRestock.value = orders
    }

    val isValid: StateFlow<Boolean> = combine(
        _produkRestock,
        _buktiTransfer,
        _tambahOrderDistributorState
    ) { list, buktiTranser, state ->
        val hasQuantity = list.any { (it.quantity ?: 0) > 0 }
        val hasBukti = buktiTranser != Uri.EMPTY
        hasQuantity && hasBukti && !state.isLoading
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        false
    )

    val totalHarga: StateFlow<Int> = _produkRestock
        .map { list -> list.sumOf { (it.quantity ?: 0) * it.item.hargaKartonPabrik } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    fun updateQuantity(productId: Int, quantity: Int) {
        val currentList = _produkRestock.value.toMutableList()
        val productIndex = currentList.indexOfFirst { it.item.idMasterBarang == productId }

        if (productIndex != -1) {
            // Make sure we create a new object to trigger proper State updates
            val updatedItem = currentList[productIndex].copy(quantity = quantity)
            currentList[productIndex] = updatedItem
            // Explicitly create a new list to ensure flow emission
            _produkRestock.value = currentList.toList()
        }
    }


    fun submitOrder() {
        _tambahOrderDistributorState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val data = distributorOrderUseCase.invoke(
                _produkRestock.value,
                totalHarga.value,
                _buktiTransfer.value
            )

            when (data) {
                is DataResult.Error -> _tambahOrderDistributorState.update {
                    it.copy(
                        isLoading = false, isSubmitted = false, errorMessage = when (data.error) {
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

                is DataResult.Success -> _tambahOrderDistributorState.update {
                    it.copy(
                        isLoading = false,
                        isSubmitted = true
                    )
                }
            }
        }
    }

    fun updateBuktiTransfer(uri: Uri) {
        _buktiTransfer.value = uri
    }
}