package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.detailRestok

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DownloadNotaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailRestokPabrikViewModel(private val useCase: DownloadNotaUseCase) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _downloadSuccess = MutableStateFlow<Boolean>(false)
    val downloadSuccess: StateFlow<Boolean> get() = _downloadSuccess

    private val _downloadError = MutableStateFlow<String?>(null)
    val downloadError: StateFlow<String?> get() = _downloadError

    fun downloadNota(idNota: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    useCase.invoke(idNota)
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
