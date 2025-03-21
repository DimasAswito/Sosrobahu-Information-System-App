package com.polije.sosrobahufactoryapp.domain.pabrik.repositiory

import DashboardPabrikResponse
import com.polije.sosrobahufactoryapp.model.LoginResponse
import com.polije.sosrobahufactoryapp.utils.DataResult


interface PabrikRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, String>
    suspend fun getDashboardPabrik(): DataResult<DashboardPabrikResponse, String>
    suspend fun getToken() : String
    suspend fun logout()
}