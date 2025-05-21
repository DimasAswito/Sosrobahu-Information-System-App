package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.data.model.pabrik.DetailOrderPabrikResponse
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

class DetailPesananMasukPabrikUseCase(private val pabrikRepository: PabrikRepository) {
    suspend operator fun invoke(idOder: Int): DataResult<DetailOrderPabrikResponse, HttpErrorCode> =
        pabrikRepository.getDetailPesananMasuk(idOder)
}