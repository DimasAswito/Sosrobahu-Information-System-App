package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.data.model.pabrik.ProdukRestokItem
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.GetItemRestockPabrikUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
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

    private val _selectedProducts = MutableStateFlow<List<SelectedProdukRestok>>(emptyList())
    val selectedProducts: StateFlow<List<SelectedProdukRestok>> = _selectedProducts

    fun fetchProdukRestok() {
        viewModelScope.launch {
            _productsState.value = PilihProdukRestockPabrikState.Loading
            try {
                val result = getItemRestockPabrikUseCase.invoke()
                when (result) {
                    is DataResult.Error -> _productsState.value =
                        PilihProdukRestockPabrikState.Error(result.message ?: "Terjadi kesalahan")

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
            current.add(SelectedProdukRestok(item = item))
        }

        _selectedProducts.value = current
    }

    fun updateQuantity(id: Int, newQty: Int) {
        _selectedProducts.value = _selectedProducts.value.map {
            if (it.item.idMasterBarang == id) it.copy(quantity = newQty)
            else it
        }
    }

    fun submitToServer() {
        val payload = _selectedProducts.value.map {
            mapOf(
                "id_barang" to it.item.idMasterBarang,
                "qty" to it.quantity
            )
        }

        // Kirim ke API
        // repository.submitRestok(payload)
    }
}

@Parcelize
data class SelectedProdukRestok(
    val item: ProdukRestokItem,
    var quantity: Int = 0,

    var hasFocus: Boolean = false,
    var cursorPosition: Int = -1
) : Parcelable