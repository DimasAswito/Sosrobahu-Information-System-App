package com.polije.sosrobahufactoryapp.domain.usecase.pabrik

import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository

class DownloadNotaUseCase(private val repository : PabrikRepository) {
    suspend operator fun invoke(idNota : Int) = repository.downloadNotaPabrik(idNota)
}