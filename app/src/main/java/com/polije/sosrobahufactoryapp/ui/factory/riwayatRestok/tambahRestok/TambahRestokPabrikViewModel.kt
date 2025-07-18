package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.InsertRestockPabrikUseCase
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.SelectedProdukRestokPabrik
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TambahRestokPabrikViewModel(private val insertRestockPabrikUseCase: InsertRestockPabrikUseCase) :
    ViewModel() {

    private val _produkRestock = MutableStateFlow<List<SelectedProdukRestokPabrik>>(emptyList())
    val produkRestock: StateFlow<List<SelectedProdukRestokPabrik>> get() = _produkRestock

    val isValid: StateFlow<Boolean> = _produkRestock
        .map { list -> list.all { it.quantity > 0 } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val _state = MutableStateFlow<TambahRestokPabrikState>(TambahRestokPabrikState.Initial)
    val state: StateFlow<TambahRestokPabrikState> get() = _state

    fun insertRestock() {

        val orders = mapOf(
            "quantities" to _produkRestock.value.associate {
                it.item.idMasterBarang.toString() to it.quantity
            }
        )
        _state.value = TambahRestokPabrikState.Loading

        viewModelScope.launch {
            val result = insertRestockPabrikUseCase.invoke(orders)

            when (result) {
                is DataResult.Error -> {
                    _state.value = TambahRestokPabrikState.Failure
                }

                is DataResult.Success -> _state.value = TambahRestokPabrikState.Success
            }
        }
    }

    fun initialProdukRestock(orders: List<SelectedProdukRestokPabrik>) {
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