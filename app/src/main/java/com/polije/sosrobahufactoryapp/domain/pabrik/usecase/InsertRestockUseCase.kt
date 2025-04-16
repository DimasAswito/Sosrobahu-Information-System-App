package com.polije.sosrobahufactoryapp.domain.pabrik.usecase

import com.polije.sosrobahufactoryapp.data.model.ProdukRestok
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult

class InsertRestockUseCase (private val pabrikRepository: PabrikRepository) {
    suspend operator fun invoke(orders :Map<String, Map<String,Int>> ): DataResult<Boolean, String> =
        pabrikRepository.insertRestock(orders)
}