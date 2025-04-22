package com.polije.sosrobahufactoryapp.ui.factory.home

import DashboardResponse
import PesananPerBulan


sealed class HomePabrikState {
    data class Success(
        val dashboardPabrik: DashboardResponse,
        val pendapatanBulanan: Map<String, PesananPerBulan>
    ) : HomePabrikState()

    data class Failure(
        val errorMessage: String
    ) : HomePabrikState()

    data object Initial : HomePabrikState()

    data object Loading : HomePabrikState()
}
