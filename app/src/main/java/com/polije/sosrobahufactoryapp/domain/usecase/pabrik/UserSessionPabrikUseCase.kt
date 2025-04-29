package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow

class UserSessionPabrikUseCase(private val pabrikRepository: PabrikRepository) {
    fun invoke() : Flow<UserSession> {
        return pabrikRepository.getUserPabrikSession()
    }

    fun isLoggingIn() = pabrikRepository.isUserIsLogged()
}