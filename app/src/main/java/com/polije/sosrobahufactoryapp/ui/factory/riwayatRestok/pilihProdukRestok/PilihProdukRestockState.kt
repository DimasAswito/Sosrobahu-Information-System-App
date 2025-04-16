package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok

import com.polije.sosrobahufactoryapp.data.model.ProdukRestok

sealed class PilihProdukRestockState {
        object Loading : PilihProdukRestockState()
        data class Success(val data: ProdukRestok) : PilihProdukRestockState()
        data class Error(val message: String) : PilihProdukRestockState()

}