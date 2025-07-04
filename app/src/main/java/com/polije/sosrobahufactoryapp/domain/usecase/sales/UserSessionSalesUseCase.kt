package com.polije.sosrobahufactoryapp.domain.usecase.sales

import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow

class UserSessionSalesUseCase(private val agenRepository: AgenRepository) {
    operator fun invoke(): Flow<Boolean> {
        return agenRepository.isUserIsLogged(UserRole.SALES)
    }

}