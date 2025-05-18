package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.detailRestok

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.DownloadNotaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailRestokPabrikViewModel(private val useCase: DownloadNotaUseCase) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _downloadSuccess = MutableLiveData<Boolean>()
    val downloadSuccess: LiveData<Boolean> get() = _downloadSuccess

    private val _downloadError = MutableLiveData<String?>()
    val downloadError: LiveData<String?> get() = _downloadError

    fun downloadNota(idNota: Int) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    useCase.invoke(idNota)
                }
                _downloadSuccess.postValue(true)
            } catch (e: Exception) {
                _downloadError.postValue(e.message ?: "Terjadi kesalahan")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
