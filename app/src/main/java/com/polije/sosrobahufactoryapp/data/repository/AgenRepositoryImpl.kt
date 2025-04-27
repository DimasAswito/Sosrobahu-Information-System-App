package com.polije.sosrobahufactoryapp.data.repository

import com.polije.sosrobahufactoryapp.data.datasource.local.TokenManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.agen.AgenDatasource
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.UserRole

class AgenRepositoryImpl(private val agenDatasource: AgenDatasource,private val tokenManager: TokenManager) : AgenRepository {
    override suspend fun login(
        username: String,
        password: String
    ): DataResult<LoginResponse, String> {
        val request = LoginRequest(username, password)
        return try {
            val data = agenDatasource.login(request)
            tokenManager.saveToken(data.token?.plainTextToken ?: "")
            tokenManager.saveUserRole(UserRole.DISTRIBUTOR)
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error(e.cause.toString(), e.message.toString())
        }
    }

    
}