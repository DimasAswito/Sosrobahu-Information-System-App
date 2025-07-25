package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import DashboardResponse
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

class DashboardPabrikUseCase(private val repository: PabrikRepository) {
    suspend operator fun invoke() : DataResult<DashboardResponse, HttpErrorCode> {
        return repository.getDashboardPabrik()
    }
}