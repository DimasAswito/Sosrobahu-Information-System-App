package com.polije.sosrobahufactoryapp.ui.sales.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.sales.OrderSalesUseCase

class OrderSalesViewModel(private val orderSalesUseCase: OrderSalesUseCase) : ViewModel() {
    fun orderSalesAgen() = orderSalesUseCase.invoke().cachedIn(viewModelScope)
}


