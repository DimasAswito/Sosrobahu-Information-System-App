package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.data.model.pabrik.DetailOrderResponse
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult

class DetailPesananMasukUseCase(private val pabrikRepository: PabrikRepository) {
    suspend operator fun invoke(idOder: Int): DataResult<DetailOrderResponse, String> =
        pabrikRepository.getDetailPesananMasuk(idOder)
}