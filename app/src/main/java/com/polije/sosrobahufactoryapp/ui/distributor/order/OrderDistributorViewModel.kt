package com.polije.sosrobahufactoryapp.ui.distributor.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.RiwayatOrderDistributorUseCase

class OrderDistributorViewModel(riwayatOrderDistributorUseCase: RiwayatOrderDistributorUseCase) :
    ViewModel() {
    val riwayatOrderDistributor = riwayatOrderDistributorUseCase.invoke().cachedIn(viewModelScope)
}