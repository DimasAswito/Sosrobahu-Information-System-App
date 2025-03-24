package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.PesananMasukUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class PesananViewModel(val pesananMasukUseCase: PesananMasukUseCase) : ViewModel() {

    private val reloadTrigger = MutableStateFlow(Unit)

    @OptIn(ExperimentalCoroutinesApi::class)
    val getPesananMasuk = reloadTrigger.flatMapLatest {
        pesananMasukUseCase.invoke().cachedIn(viewModelScope)
    }
}