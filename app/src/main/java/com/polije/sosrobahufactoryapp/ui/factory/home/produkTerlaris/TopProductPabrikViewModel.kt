package com.polije.sosrobahufactoryapp.ui.factory.home.produkTerlaris

import DashboardResponse
import android.util.Log
import androidx.lifecycle.ViewModel
import com.polije.sosrobahufactoryapp.data.model.pabrik.TopSellingProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TopProductPabrikViewModel : ViewModel() {

    private val _topProducts = MutableStateFlow<List<TopSellingProduct>>(emptyList())
    val topProducts: StateFlow<List<TopSellingProduct>> = _topProducts

    fun setDashboardData(dashboard: DashboardResponse) {
        val namaList = dashboard.namaRokokList
        val gambarList = dashboard.gambarRokokList
        val stokList = dashboard.totalProdukList

        if (namaList.size != gambarList.size || namaList.size != stokList.size) {
            Log.e("TopProductVM", "Jumlah listBarangAgen tidak konsisten")
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
