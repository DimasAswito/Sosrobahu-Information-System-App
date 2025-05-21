package com.polije.sosrobahufactoryapp.ui.agen.home

import com.polije.sosrobahufactoryapp.data.model.agen.DashboardAgenResponse


sealed class HomeAgenState {
    data class Success(
        val dashboardResponse: DashboardAgenResponse
    ) : HomeAgenState()

    data class Failure(
        val errorMessage: String
    ) : HomeAgenState()

    data object Initial : HomeAgenState()

    data object Loading : HomeAgenState()
}