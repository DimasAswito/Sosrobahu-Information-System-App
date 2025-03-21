package com.polije.sosrobahufactoryapp.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class LoginResponse(

	@SerialName("message")
	val message: String? = null,

	@SerialName("user")
	val user: User? = null,

	@SerialName("token")
	val token: Token? = null
)

@Serializable
data class Token(

	@SerialName("plainTextToken")
	val plainTextToken: String? = null,

	@SerialName("accessToken")
	val accessToken: AccessToken? = null
)

@Serializable
data class AccessToken(

	@SerialName("abilities")
	val abilities: List<String?>? = null,

	@SerialName("expires_at")
	val expiresAt: String? = null,

	@SerialName("tokenable_id")
	val tokenableId: Int? = null,

	@SerialName("tokenable_type")
	val tokenableType: String? = null,

	@SerialName("updated_at")
	val updatedAt: String? = null,

	@SerialName("user_id")
	val userId: Int? = null,

	@SerialName("name")
	val name: String? = null,

	@SerialName("created_at")
	val createdAt: String? = null,

	@SerialName("id")
	val id: Int? = null
)

@Serializable
data class User(

	@SerialName("role")
	val role: String? = null,

	@SerialName("nama_lengkap")
	val namaLengkap: String? = null,

	@SerialName("id")
	val id: Int? = null
)
