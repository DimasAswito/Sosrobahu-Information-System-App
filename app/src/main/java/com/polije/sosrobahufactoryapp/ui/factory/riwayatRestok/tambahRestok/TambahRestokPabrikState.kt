package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok

sealed class TambahRestokPabrikState{
    data object Failure : TambahRestokPabrikState()

    data object Success : TambahRestokPabrikState()

    data object Initial : TambahRestokPabrikState()

    data object Loading : TambahRestokPabrikState()
}