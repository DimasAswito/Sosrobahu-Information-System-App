package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.PesananMasukUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class PesananPabrikViewModel(pesananMasukUseCase: PesananMasukUseCase) : ViewModel() {

    val getPesananMasuk =
        pesananMasukUseCase.invoke().cachedIn(viewModelScope).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), PagingData.empty()
        )
}