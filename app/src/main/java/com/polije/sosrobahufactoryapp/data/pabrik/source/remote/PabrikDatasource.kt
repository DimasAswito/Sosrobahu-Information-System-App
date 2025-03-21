package com.polije.sosrobahufactoryapp.data.pabrik.source.remote

import DashboardPabrikResponse
import com.polije.sosrobahufactoryapp.model.LoginRequest
import com.polije.sosrobahufactoryapp.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PabrikDatasource {
    @POST("pabrik/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("pabrik/dashboard")
    suspend fun getDashboardPabrik(@Header("Authorization") token: String): Response<DashboardPabrikResponse>
}