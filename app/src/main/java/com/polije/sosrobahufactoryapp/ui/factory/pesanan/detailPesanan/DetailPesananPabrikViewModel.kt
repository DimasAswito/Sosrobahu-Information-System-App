package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.DetailPesananMasukUseCase
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.UpdatePesananUseCase
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class DetailPesananPabrikViewModel(
    private val getDetailPesananMasukUseCase: DetailPesananMasukUseCase,
    private val updateDetailPesananMasukUseCase: UpdatePesananUseCase
) : ViewModel() {
    private val _detailPesanan = MutableStateFlow<DetailPesananPabrikState>(DetailPesananPabrikState.Initial)
    val detailPesanan get() = _detailPesanan.asStateFlow()

    private val _updatePesananState = MutableStateFlow<UpdateStatusPesananPabrikState>(
        UpdateStatusPesananPabrikState.Initial
    )
    val updatePesananState get() = _updatePesananState.asStateFlow()


    fun detailPesananMasuk(idOrder: Int) {
        _detailPesanan.value = DetailPesananPabrikState.Loading
        viewModelScope.launch {
            try {
                val data = getDetailPesananMasukUseCase.invoke(idOrder)
                when (data) {
                    is DataResult.Error -> _detailPesanan.value =
                        DetailPesananPabrikState.Failure(data.message)

                    is DataResult.Success -> _detailPesanan.value =
                        DetailPesananPabrikState.Success(data.data)
                }
            } catch (e: Exception) {
                _detailPesanan.value =
                    DetailPesananPabrikState.Failure(e.message.toString())
            }
        }
    }

    fun updateDetailPesanan(idOrder: Int, status: Int) {
        _updatePesananState.value = UpdateStatusPesananPabrikState.Loading
        viewModelScope.launch {
            try {
                val data = updateDetailPesananMasukUseCase.invoke(idOrder, status)
                when (data) {
                    is DataResult.Error -> {
                        _updatePesananState.value = UpdateStatusPesananPabrikState.Failure(data.message)
                    }

                    is DataResult.Success -> {
                        delay(2.seconds)
                        _updatePesananState.value = UpdateStatusPesananPabrikState.Success
                    }
                }
            } catch (e: Exception) {
                _detailPesanan.value =
                    DetailPesananPabrikState.Failure(e.message.toString())
            }
        }
    }


}