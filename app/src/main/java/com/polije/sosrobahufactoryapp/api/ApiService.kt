package com.polije.sosrobahufactoryapp.api

import com.polije.sosrobahufactoryapp.model.LoginRequest
import com.polije.sosrobahufactoryapp.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("pabrik/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}
