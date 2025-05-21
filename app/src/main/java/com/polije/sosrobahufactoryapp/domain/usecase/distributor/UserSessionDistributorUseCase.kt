package com.polije.sosrobahufactoryapp.domain.usecase.distributor

import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow

class UserSessionDistributorUseCase(private val distributorRepository: DistributorRepository) {
    operator fun invoke(): Flow<Boolean> =
        distributorRepository.isUserIsLogged(UserRole.DISTRIBUTOR)

}