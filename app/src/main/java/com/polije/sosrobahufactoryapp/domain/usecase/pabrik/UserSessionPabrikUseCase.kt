package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow

class UserSessionPabrikUseCase(private val pabrikRepository: PabrikRepository) {
    fun invoke(): Flow<Boolean> {
        return pabrikRepository.isUserIsLogged(UserRole.PABRIK)
    }

}