package com.polije.sosrobahufactoryapp.ui.distributor.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.RiwayatOrderDistributorUseCase

class OrderDistributorViewModel(private val riwayatOrderDistributorUseCase: RiwayatOrderDistributorUseCase) :
    ViewModel() {
    fun riwayatOrderDistributor() = riwayatOrderDistributorUseCase.invoke().cachedIn(viewModelScope)
}