package com.polije.sosrobahufactoryapp.ui.distributor.order.tambahOrder

import androidx.lifecycle.ViewModel
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TambahOrderDistributorViewModel() : ViewModel() {
    private val _produkRestock = MutableStateFlow<List<SelectedProdukDistributor>>(emptyList())
    val produkRestock: StateFlow<List<SelectedProdukDistributor>> get() = _produkRestock


    fun initialProdukRestock(orders: List<SelectedProdukDistributor>) {
        _produkRestock.value = orders
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        val currentList = _produkRestock.value?.toMutableList() ?: mutableListOf()
        val productIndex = currentList.indexOfFirst { it.item.idMasterBarang == productId }

        if (productIndex != -1) {
            currentList[productIndex] = currentList[productIndex].copy(quantity = quantity)
            _produkRestock.value = currentList
        }
    }
}