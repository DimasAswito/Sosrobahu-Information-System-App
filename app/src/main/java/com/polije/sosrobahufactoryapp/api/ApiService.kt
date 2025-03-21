package com.polije.sosrobahufactoryapp.api

import DashboardPabrikResponse
import com.polije.sosrobahufactoryapp.model.LoginRequest
import com.polije.sosrobahufactoryapp.model.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("pabrik/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("pabrik/dashboard")
    suspend fun getDashboardPabrik(@Header("Authorization") token: String): Response<DashboardPabrikResponse>
}
