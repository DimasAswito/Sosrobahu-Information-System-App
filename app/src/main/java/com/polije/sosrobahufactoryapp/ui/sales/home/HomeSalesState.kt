package com.polije.sosrobahufactoryapp.ui.sales.home

import com.polije.sosrobahufactoryapp.data.model.agen.DashboardAgenResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DashboardSalesResponse

sealed class HomeSalesState {
    data class Success(
        val dashboardResponse: DashboardSalesResponse
    ) : HomeSalesState()

    data class Failure(
        val errorMessage: String
    ) : HomeSalesState()

    data object Initial : HomeSalesState()

    data object Loading : HomeSalesState()
}