package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.DetailPesananMasukUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailPesananViewModel(private val useCase: DetailPesananMasukUseCase) : ViewModel() {
    private val _detailPesanan = MutableStateFlow<DetailPesananState>(DetailPesananState.Initial)
    val detailPesanan get() = _detailPesanan.asStateFlow()


    fun detailPesananMasuk(idOrder: Int) {
        _detailPesanan.value = DetailPesananState.Loading
        viewModelScope.launch {
            try {
                val data = useCase.invoke(idOrder)
                when (data) {
                    is DataResult.Error -> _detailPesanan.value =
                        DetailPesananState.Failure(data.message)

                    is DataResult.Success -> _detailPesanan.value =
                        DetailPesananState.Success(data.data)
                }
            }catch (e : Exception) {
                _detailPesanan.value =
                    DetailPesananState.Failure(e.message.toString())
            }
        }
    }
}