package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.data.model.pabrik.ProdukRestok
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

class GetItemRestockPabrikUseCase(private val pabrikRepository: PabrikRepository) {
    suspend operator fun invoke(): DataResult<ProdukRestok, HttpErrorCode> =
        pabrikRepository.getItemRestock()
}