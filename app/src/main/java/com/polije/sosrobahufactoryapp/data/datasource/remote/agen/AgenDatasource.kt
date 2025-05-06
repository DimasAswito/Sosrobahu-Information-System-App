package com.polije.sosrobahufactoryapp.data.datasource.remote.agen

import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DashboardAgenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AgenDatasource {
    @POST("agen/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("agen/dashboard")
    suspend fun getDashboardAgen(@Header("Authorization") token: String): DashboardAgenResponse
}