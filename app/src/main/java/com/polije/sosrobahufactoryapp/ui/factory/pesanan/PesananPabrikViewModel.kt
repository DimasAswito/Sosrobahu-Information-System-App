package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.PesananMasukPabrikUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class PesananPabrikViewModel(val pesananMasukUseCase: PesananMasukPabrikUseCase) : ViewModel() {

    fun pesananResult() =
        pesananMasukUseCase.invoke()
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PagingData.empty()
            )
}