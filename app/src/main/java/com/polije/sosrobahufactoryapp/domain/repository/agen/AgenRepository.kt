package com.polije.sosrobahufactoryapp.domain.repository.agen

import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DashboardAgenResponse
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow

interface AgenRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, HttpErrorCode>
    suspend fun getDashboardAgen(): DataResult<DashboardAgenResponse, HttpErrorCode>

    fun getUserAgenSession(): Flow<UserSession>
    fun isUserIsLogged(): Flow<Boolean>
    suspend fun logout()
}