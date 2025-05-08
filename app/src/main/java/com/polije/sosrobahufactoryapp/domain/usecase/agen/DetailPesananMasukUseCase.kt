package com.polije.sosrobahufactoryapp.domain.usecase.agen

import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository

class DetailPesananMasukUseCase(private val repository: AgenRepository) {
    suspend operator fun invoke(idOrder : Int) = repository.getDetailPesananMasukAgen(idOrder)
}