package com.polije.sosrobahufactoryapp.domain.usecase.agen

import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository

class DownloadNotaAgenUseCase(private val repository: AgenRepository) {
    suspend operator fun invoke(idNota: Int) = repository.downloadNota(idNota)
}