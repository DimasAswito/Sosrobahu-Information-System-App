package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow

class CheckLoginPabrikUseCase(private val pabrikRepository: PabrikRepository) {
    operator fun invoke() : Flow<UserRole?> {
        return pabrikRepository.isLoggingUsingPabrik()
    }
}