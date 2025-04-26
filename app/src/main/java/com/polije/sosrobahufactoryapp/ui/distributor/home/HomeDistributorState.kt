package com.polije.sosrobahufactoryapp.ui.distributor.home

import DashboardResponse
import PesananPerBulan

sealed class HomeDistributorState {
    data class Success(
        val dashboardResponse: DashboardResponse  // ganti dengan data dari distributor
    ) : HomeDistributorState()

    data class Failure(
        val errorMessage: String
    ) : HomeDistributorState()

    data object Initial : HomeDistributorState()

    data object Loading : HomeDistributorState()
}