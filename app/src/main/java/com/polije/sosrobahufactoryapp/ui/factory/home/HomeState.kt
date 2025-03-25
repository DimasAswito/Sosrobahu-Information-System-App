package com.polije.sosrobahufactoryapp.ui.factory.home

import DashboardPabrikResponse
import PesananPerBulan


sealed class HomeState {
    data class Success(
        val dashboardPabrik: DashboardPabrikResponse,
        val pendapatanBulanan: Map<String, PesananPerBulan>
    ) : HomeState()

    data class Failure(
        val errorMessage: String
    ) : HomeState()

    data object Initial : HomeState()

    data object Loading : HomeState()
}
