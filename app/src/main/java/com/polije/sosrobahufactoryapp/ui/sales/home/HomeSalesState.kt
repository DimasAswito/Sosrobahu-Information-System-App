package com.polije.sosrobahufactoryapp.ui.sales.home

import com.polije.sosrobahufactoryapp.data.model.sales.DashboardSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.ListBarangAgenSalesResponse

//sealed class HomeSalesState {
//    data class Success(
//        val dashboardResponse: DashboardSalesResponse
//    ) : HomeSalesState()
//
//    data class Failure(
//        val errorMessage: String
//    ) : HomeSalesState()
//
//    data object Initial : HomeSalesState()
//
//    data object Loading : HomeSalesState()
//}

data class HomeSalesState(
    val isLoading: Boolean = false,
    val dashboard: DashboardSalesResponse? = null,
    val barangReponse: ListBarangAgenSalesResponse? = null,
    val errorMessage: String? = null,
)