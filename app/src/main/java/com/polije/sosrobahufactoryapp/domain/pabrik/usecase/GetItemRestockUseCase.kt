package com.polije.sosrobahufactoryapp.domain.pabrik.usecase

import com.polije.sosrobahufactoryapp.data.model.pabrik.ProdukRestok
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult

class GetItemRestockUseCase(private val pabrikRepository: PabrikRepository) {
    suspend operator fun invoke(): DataResult<ProdukRestok, String> =
        pabrikRepository.getItemRestock()
}