package com.polije.sosrobahufactoryapp.domain.usecase.agen

import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository

class UploadNewBarangAgenUseCase(private val repository: AgenRepository) {
    suspend operator fun invoke(ids: List<Int>) = repository.uploadBarangTerbaru(ids)
}