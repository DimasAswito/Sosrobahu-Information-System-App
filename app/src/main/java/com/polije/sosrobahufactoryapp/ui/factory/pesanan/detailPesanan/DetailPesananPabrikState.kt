package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import com.polije.sosrobahufactoryapp.data.model.pabrik.DetailOrderResponse

sealed class DetailPesananPabrikState {
    data class Success(val data: DetailOrderResponse) : DetailPesananPabrikState()

    data class Failure(
        val errorMessage: String
    ) : DetailPesananPabrikState()

    data object Initial : DetailPesananPabrikState()

    data object Loading : DetailPesananPabrikState()
}