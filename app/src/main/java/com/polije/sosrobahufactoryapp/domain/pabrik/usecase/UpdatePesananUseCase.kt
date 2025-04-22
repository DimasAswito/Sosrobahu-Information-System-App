package com.polije.sosrobahufactoryapp.domain.pabrik.usecase

import com.polije.sosrobahufactoryapp.data.model.UpdateDetailPesananRequestResponse
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult

class UpdatePesananUseCase(private val repository: PabrikRepository) {
    suspend operator fun invoke(
        idOrder: Int,
        status: Int
    ): DataResult<UpdateDetailPesananRequestResponse, String> =
        repository.updateDetailPesananMasuk(idOrder, status)
}