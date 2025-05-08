package com.polije.sosrobahufactoryapp.domain.usecase.agen

import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository

class PesananMasukAgenUseCase(private val repository: AgenRepository) {
    operator fun invoke() = repository.getPesananMasukAgen()
}