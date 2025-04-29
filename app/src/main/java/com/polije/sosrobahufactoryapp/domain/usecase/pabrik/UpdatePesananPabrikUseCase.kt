package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananResponse
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

class UpdatePesananPabrikUseCase(private val repository: PabrikRepository) {
    suspend operator fun invoke(
        idOrder: Int,
        status: Int
    ): DataResult<UpdateDetailPesananResponse, HttpErrorCode> =
        repository.updateDetailPesananMasuk(idOrder, status)
}