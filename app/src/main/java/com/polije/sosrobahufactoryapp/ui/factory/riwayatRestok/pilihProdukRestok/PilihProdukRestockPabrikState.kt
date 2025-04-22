package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok

import com.polije.sosrobahufactoryapp.data.model.pabrik.ProdukRestok

sealed class PilihProdukRestockPabrikState {
        object Loading : PilihProdukRestockPabrikState()
        data class Success(val data: ProdukRestok) : PilihProdukRestockPabrikState()
        data class Error(val message: String) : PilihProdukRestockPabrikState()

}