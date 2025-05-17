package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.detailRestok

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DownloadNotaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailRestokPabrikViewModel(private val useCase: DownloadNotaUseCase) : ViewModel() {

    fun downloadNota(idNota: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                useCase.invoke(idNota)
            }
        }
    }

}