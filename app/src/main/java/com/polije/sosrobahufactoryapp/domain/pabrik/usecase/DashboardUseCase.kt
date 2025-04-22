package com.polije.sosrobahufactoryapp.domain.pabrik.usecase

import DashboardResponse
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult

class DashboardUseCase(private val repository: PabrikRepository) {
    suspend operator fun invoke() : DataResult<DashboardResponse, String> {
        return repository.getDashboardPabrik()
    }
}