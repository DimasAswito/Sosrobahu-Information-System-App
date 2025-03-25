package com.polije.sosrobahufactoryapp.domain.pabrik.usecase

import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.RiwayatRestockItem
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import kotlinx.coroutines.flow.Flow

class RiwayatRestokUseCase(private val pabrikRepository: PabrikRepository) {
    operator fun invoke(query: String): Flow<PagingData<RiwayatRestockItem>> =
        pabrikRepository.getRiwayatRestockPabrik(query)
}