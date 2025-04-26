package com.polije.sosrobahufactoryapp.ui.agen.home

import DashboardResponse

sealed class HomeAgenState {
    data class Success(
        val dashboardResponse: DashboardResponse  // ganti dengan data dari Agen
    ) : HomeAgenState()

    data class Failure(
        val errorMessage: String
    ) : HomeAgenState()

    data object Initial : HomeAgenState()

    data object Loading : HomeAgenState()
}