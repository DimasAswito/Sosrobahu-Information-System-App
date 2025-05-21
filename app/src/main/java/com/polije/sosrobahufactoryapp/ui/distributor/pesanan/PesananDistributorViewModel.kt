package com.polije.sosrobahufactoryapp.ui.distributor.pesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.distributor.PesananMasukDistributorUseCase

class PesananDistributorViewModel(private val pesananMasukDistributorUseCase: PesananMasukDistributorUseCase) :
    ViewModel() {
    fun pesananMasukDistributor() = pesananMasukDistributorUseCase.invoke().cachedIn(viewModelScope)
}