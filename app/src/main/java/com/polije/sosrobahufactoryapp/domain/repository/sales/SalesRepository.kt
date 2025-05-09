package com.polije.sosrobahufactoryapp.domain.repository.sales

import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DashboardSalesResponse
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow

interface SalesRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, HttpErrorCode>

    suspend fun getDashboardSales(): DataResult<DashboardSalesResponse, HttpErrorCode>

    fun isUserIsLogged(requiredRole: UserRole): Flow<Boolean>
    suspend fun logout()

}