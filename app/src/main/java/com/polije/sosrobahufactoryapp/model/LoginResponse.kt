package com.polije.sosrobahufactoryapp.model

data class LoginResponse(
    val message: String,
    val token: TokenData,
    val user: UserData
)

data class TokenData(
    val accessToken: AccessToken,
    val plainTextToken: String
)

data class AccessToken(
    val name: String,
    val abilities: List<String>,
    val tokenable_id: Int,
    val tokenable_type: String,
    val updated_at: String,
    val created_at: String,
    val id: Int,
    val user_id: Int
)

data class UserData(
    val id: Int,
    val nama_lengkap: String,
    val role: String
)
