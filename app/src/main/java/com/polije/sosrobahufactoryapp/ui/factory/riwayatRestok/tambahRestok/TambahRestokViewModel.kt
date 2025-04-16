package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.InsertRestockUseCase
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.SelectedProdukRestok
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.map

class TambahRestokViewModel(private val insertRestockUseCase: InsertRestockUseCase) : ViewModel() {

    private val _produkRestock = MutableStateFlow<List<SelectedProdukRestok>>(emptyList())
    val produkRestock : StateFlow<List<SelectedProdukRestok>> get() = _produkRestock

    val isValid: StateFlow<Boolean> = _produkRestock
        .map { list -> list.all { it.quantity > 0 } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val _state = MutableStateFlow<TambahRestokState>(TambahRestokState.Initial)
    val state: StateFlow<TambahRestokState> get() = _state

    fun insertRestock() {

        val orders = mapOf(
            "quantities" to _produkRestock.value.associate {
                it.item.idMasterBarang.toString() to it.quantity
            }
        )
        _state.value = TambahRestokState.Loading

        viewModelScope.launch {
            val result = insertRestockUseCase.invoke(orders)

            when (result) {
                is DataResult.Error -> {
                    _state.value = TambahRestokState.Failure
                }

                is DataResult.Success -> _state.value = TambahRestokState.Success
            }
        }
    }

    fun initialProdukRestock(orders : List<SelectedProdukRestok>){
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