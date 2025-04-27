package com.polije.sosrobahufactoryapp.data.datasource.remote.agen

import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AgenDatasource {
    @POST("agen/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}