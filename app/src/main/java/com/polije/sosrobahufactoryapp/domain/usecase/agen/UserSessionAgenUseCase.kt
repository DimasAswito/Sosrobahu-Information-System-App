package com.polije.sosrobahufactoryapp.domain.usecase.agen

import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow

class UserSessionAgenUseCase(private val agenRepository: AgenRepository) {
    operator fun invoke(): Flow<UserSession> {
        return agenRepository.getUserAgenSession()
    }

    fun isLoggingIn() = agenRepository.isUserIsLogged()

}