package com.polije.sosrobahufactoryapp.ui.sales.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.sales.RiwayatOrderSalesUseCase

class OrderSalesViewModel(private val riwayatOrderSalesUseCase: RiwayatOrderSalesUseCase) : ViewModel() {
    fun orderSalesAgen() = riwayatOrderSalesUseCase.invoke().cachedIn(viewModelScope)
}


