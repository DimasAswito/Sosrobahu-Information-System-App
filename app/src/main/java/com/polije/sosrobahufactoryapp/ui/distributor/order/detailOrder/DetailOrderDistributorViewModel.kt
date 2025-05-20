package com.polije.sosrobahufactoryapp.ui.distributor.order.detailOrder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.DownloadNotaDistributorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailOrderDistributorViewModel(
    private val notaUseCase: DownloadNotaDistributorUseCase) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _downloadSuccess = MutableStateFlow(false)
    val downloadSuccess: StateFlow<Boolean> get() = _downloadSuccess

    private val _downloadError = MutableStateFlow<String?>(null)
    val downloadError: StateFlow<String?> get() = _downloadError

    fun downloadNota(idNota: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    notaUseCase.invoke(idNota)
                }
                _downloadSuccess.value = true
            } catch (e: Exception) {
                _downloadError.value = e.message ?: "Terjadi kesalahan"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
