package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok

import com.polije.sosrobahufactoryapp.ui.factory.home.HomeState

sealed class TambahRestokState{
    data object Failure : TambahRestokState()

    data object Success : TambahRestokState()

    data object Initial : TambahRestokState()

    data object Loading : TambahRestokState()
}