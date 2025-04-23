package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import DashboardResponse
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult

class DashboardUseCase(private val repository: PabrikRepository) {
    suspend operator fun invoke() : DataResult<DashboardResponse, String> {
        return repository.getDashboardPabrik()
    }
}