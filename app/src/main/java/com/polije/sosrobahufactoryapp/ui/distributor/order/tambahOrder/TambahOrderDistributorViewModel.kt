package com.polije.sosrobahufactoryapp.ui.distributor.order.tambahOrder

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TambahOrderDistributorViewModel() : ViewModel() {
    private val _produkRestock = MutableStateFlow<List<SelectedProdukDistributor>>(emptyList())
    val produkRestock: StateFlow<List<SelectedProdukDistributor>> get() = _produkRestock.asStateFlow()

    private val _buktiTransfer = MutableStateFlow<Uri>(Uri.EMPTY)
    val buktiTransfer: StateFlow<Uri> get() = _buktiTransfer.asStateFlow()

    fun initialProdukRestock(orders: List<SelectedProdukDistributor>) {
        _produkRestock.value = orders
    }

    val isValid: StateFlow<Boolean> = _produkRestock
        .map { list -> list.any { (it.quantity ?: 0) > 0 } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

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

    fun updateBuktiTransfer(uri: Uri) {
        _buktiTransfer.value = uri
    }
}