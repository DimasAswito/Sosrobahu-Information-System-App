package com.polije.sosrobahufactoryapp.ui.agen.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.agen.RiwayatOrderAgenUseCase

class OrderAgenViewModel(private val riwayatOrderAgenUseCase: RiwayatOrderAgenUseCase) : ViewModel() {
    fun getOrderAgen() = riwayatOrderAgenUseCase.invoke().cachedIn(viewModelScope)
}