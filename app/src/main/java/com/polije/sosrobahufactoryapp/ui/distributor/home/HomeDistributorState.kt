package com.polije.sosrobahufactoryapp.ui.distributor.home

import DashboardDistributorResponse
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

sealed class HomeDistributorState {
    data class Success(
        val dashboardResponse: DashboardDistributorResponse// ganti dengan listBarangAgen dari distributor
    ) : HomeDistributorState()

    data class Failure(
        val errorCode: HttpErrorCode,
        val errorMessage: String
    ) : HomeDistributorState()

    data object Initial : HomeDistributorState()

    data object Loading : HomeDistributorState()
}