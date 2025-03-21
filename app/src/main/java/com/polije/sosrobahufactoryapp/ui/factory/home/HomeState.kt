package com.polije.sosrobahufactoryapp.ui.factory.home

import DashboardPabrikResponse
import PesananPerBulan


data class DashboardPabrikState(
    val dashboardPabrik: DashboardPabrikResponse? = null,
    val errorMessage: String? = null,
    val pendapatanBulanan: Map<String, PesananPerBulan> = emptyMap()
)
