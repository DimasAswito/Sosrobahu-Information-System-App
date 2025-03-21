package com.polije.sosrobahufactoryapp.data.pabrik.repository

import DashboardPabrikResponse
import com.polije.sosrobahufactoryapp.data.local.TokenManager
import com.polije.sosrobahufactoryapp.data.pabrik.source.remote.PabrikDatasource
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository

import com.polije.sosrobahufactoryapp.model.LoginRequest
import com.polije.sosrobahufactoryapp.model.LoginResponse
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.first

class PabrikRepositoryImpl(val pabrikDatasource: PabrikDatasource, val tokenManager: TokenManager) :
    PabrikRepository {
    override suspend fun login(
        username: String,
        password: String
    ): DataResult<LoginResponse, String> {
        val request = LoginRequest(username, password)
        return try {
            val data = pabrikDatasource.login(request)
            tokenManager.saveToken(data.token?.plainTextToken ?: "")
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error(e.cause.toString(),e.message.toString())
        }
    }

    override suspend fun getDashboardPabrik(): DataResult<DashboardPabrikResponse, String> {

        return try {
            val token = tokenManager.getToken().first()
            val response = pabrikDatasource.getDashboardPabrik("Bearer $token")

            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    DataResult.Success(data)
                } else {
                    DataResult.Error("Not Found",response.message())
                }
            } else {
                DataResult.Error("Not Found",response.message())
            }

        } catch (e: Exception) {
            DataResult.Error("Not Found",e.message.toString())        }
    }

    override suspend fun getToken(): String {
        return tokenManager.getToken().first()
    }

    override suspend fun logout() {
        tokenManager.removeToken()
    }
}