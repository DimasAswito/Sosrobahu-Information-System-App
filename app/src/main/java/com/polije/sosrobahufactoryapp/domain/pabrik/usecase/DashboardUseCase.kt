package com.polije.sosrobahufactoryapp.domain.pabrik.usecase

import DashboardPabrikResponse
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult

class DashboardUseCase(private val repository: PabrikRepository) {
    suspend operator fun invoke() : DataResult<DashboardPabrikResponse, String> {
        return repository.getDashboardPabrik()
    }
}