package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import com.polije.sosrobahufactoryapp.data.model.DetailOrderResponse

sealed class DetailPesananState {
    data class Success(val data: DetailOrderResponse) : DetailPesananState()

    data class Failure(
        val errorMessage: String
    ) : DetailPesananState()

    data object Initial : DetailPesananState()

    data object Loading : DetailPesananState()
}