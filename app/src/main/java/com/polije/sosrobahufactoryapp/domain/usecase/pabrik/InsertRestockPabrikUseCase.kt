package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

class InsertRestockPabrikUseCase(private val pabrikRepository: PabrikRepository) {
    suspend operator fun invoke(orders: Map<String, Map<String, Int>>): DataResult<Boolean, HttpErrorCode> =
        pabrikRepository.insertRestock(orders)
}