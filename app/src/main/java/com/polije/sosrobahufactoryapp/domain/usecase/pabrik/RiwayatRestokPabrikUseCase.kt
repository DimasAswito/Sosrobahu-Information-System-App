package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.pabrik.RiwayatRestockItem
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import kotlinx.coroutines.flow.Flow

class RiwayatRestokPabrikUseCase(private val pabrikRepository: PabrikRepository) {
    operator fun invoke(query: String): Flow<PagingData<RiwayatRestockItem>> =
        pabrikRepository.getRiwayatRestockPabrik(query)
}