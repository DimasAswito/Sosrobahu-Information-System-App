package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import com.polije.sosrobahufactoryapp.data.model.pabrik.DetailOrderPabrikResponse

sealed class DetailPesananPabrikState {
    data class Success(val data: DetailOrderPabrikResponse) : DetailPesananPabrikState()

    data class Failure(
        val errorMessage: String
    ) : DetailPesananPabrikState()

    data object Initial : DetailPesananPabrikState()

    data object Loading : DetailPesananPabrikState()
}