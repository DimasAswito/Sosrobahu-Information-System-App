package com.polije.sosrobahufactoryapp.ui.agen.order.detailOrder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.agen.DownloadNotaAgenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailOrderAgenViewModel(private val downloadNotaUseCase: DownloadNotaAgenUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(DetailOrderAgenState())
    val state = _state.asStateFlow()


    fun downloadNota(idNota: Int) {

        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    downloadNotaUseCase.invoke(idNota)
                }
                _state.update { it.copy(isSubmitted = true, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message ?: "Terjadi kesalahan") }
            } finally {
                _state.update { it.copy(isSubmitted = false, isLoading = false) }
            }
        }
    }
}