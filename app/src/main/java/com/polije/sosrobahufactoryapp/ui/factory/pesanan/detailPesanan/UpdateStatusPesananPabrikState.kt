package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

sealed class UpdateStatusPesananPabrikState {
    data object Success : UpdateStatusPesananPabrikState()

    data class Failure(
        val errorMessage: String
    ) : UpdateStatusPesananPabrikState()

    data object Initial : UpdateStatusPesananPabrikState()

    data object Loading : UpdateStatusPesananPabrikState()
}