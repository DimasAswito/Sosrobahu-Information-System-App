package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.pabrik.PesananMasukItem
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import kotlinx.coroutines.flow.Flow

class PesananMasukUseCase(private val pabrikRepository: PabrikRepository) {
    operator fun invoke() : Flow<PagingData<PesananMasukItem>> = pabrikRepository.getPesananMasuk()
}