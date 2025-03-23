package com.polije.sosrobahufactoryapp.domain.pabrik.usecase

import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.DataItem
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import kotlinx.coroutines.flow.Flow

class PesananMasukUseCase(private val pabrikRepository: PabrikRepository) {
    operator fun invoke() : Flow<PagingData<DataItem>> = pabrikRepository.getPesananMasuk()
}