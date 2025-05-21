package com.polije.sosrobahufactoryapp.ui.sales.daftarToko

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.sales.TokoSalesUseCase

class DaftarTokoSalesViewModel(private val tokoSalesUseCase: TokoSalesUseCase) : ViewModel() {
    fun listToko() = tokoSalesUseCase.invoke().cachedIn(viewModelScope)
}