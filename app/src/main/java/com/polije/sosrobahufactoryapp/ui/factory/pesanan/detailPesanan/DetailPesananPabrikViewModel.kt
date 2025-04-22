package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.DetailPesananMasukUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailPesananPabrikViewModel(private val useCase: DetailPesananMasukUseCase) : ViewModel() {
    private val _detailPesanan = MutableStateFlow<DetailPesananPabrikState>(DetailPesananPabrikState.Initial)
    val detailPesanan get() = _detailPesanan.asStateFlow()


    fun detailPesananMasuk(idOrder: Int) {
        _detailPesanan.value = DetailPesananPabrikState.Loading
        viewModelScope.launch {
            try {
                val data = useCase.invoke(idOrder)
                when (data) {
                    is DataResult.Error -> _detailPesanan.value =
                        DetailPesananPabrikState.Failure(data.message)

                    is DataResult.Success -> _detailPesanan.value =
                        DetailPesananPabrikState.Success(data.data)
                }
            }catch (e : Exception) {
                _detailPesanan.value =
                    DetailPesananPabrikState.Failure(e.message.toString())
            }
        }
    }
}