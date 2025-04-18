package com.polije.sosrobahufactoryapp.ui.factory.home.produkTerlaris

import DashboardPabrikResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.polije.sosrobahufactoryapp.data.model.TopSellingProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TopProductViewModel : ViewModel() {

    private val _topProducts = MutableStateFlow<List<TopSellingProduct>>(emptyList())
    val topProducts: StateFlow<List<TopSellingProduct>> = _topProducts

    fun setDashboardData(dashboard: DashboardPabrikResponse) {
        val namaList = dashboard.namaRokokList
        val gambarList = dashboard.gambarRokokList
        val stokList = dashboard.totalProdukList

        if (namaList.size != gambarList.size || namaList.size != stokList.size) {
            Log.e("TopProductVM", "Jumlah data tidak konsisten")
            return
        }

        val products = namaList.indices.map { index ->
            TopSellingProduct(
                rank = 0, // Nanti di-set pas sorting
                name = namaList[index],
                image = gambarList[index],
                stock = stokList[index]
            )
        }.sortedBy { it.stock } // Urut dari stok paling kecil

        // Tambahkan peringkat
        val rankedProducts = products.mapIndexed { index, product ->
            product.copy(rank = index + 1)
        }

        _topProducts.value = rankedProducts
    }
}
