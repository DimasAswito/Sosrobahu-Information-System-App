package com.polije.sosrobahufactoryapp.ui.distributor.home

import DashboardResponse
import PesananPerBulan
import com.polije.sosrobahufactoryapp.data.model.distributor.DashboardDistributorResponse
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.DasbhoardDistributorUseCase
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

sealed class HomeDistributorState {
    data class Success(
        val dashboardResponse: DashboardDistributorResponse// ganti dengan data dari distributor
    ) : HomeDistributorState()

    data class Failure(
        val errorCode: HttpErrorCode,
        val errorMessage: String
    ) : HomeDistributorState()

    data object Initial : HomeDistributorState()

    data object Loading : HomeDistributorState()
}