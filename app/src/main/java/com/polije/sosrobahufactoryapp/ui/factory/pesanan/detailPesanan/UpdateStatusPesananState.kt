package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import com.polije.sosrobahufactoryapp.data.model.DetailOrderResponse

sealed class UpdateStatusPesananState {
    data object Success : UpdateStatusPesananState()

    data class Failure(
        val errorMessage: String
    ) : UpdateStatusPesananState()

    data object Initial : UpdateStatusPesananState()

    data object Loading : UpdateStatusPesananState()
}