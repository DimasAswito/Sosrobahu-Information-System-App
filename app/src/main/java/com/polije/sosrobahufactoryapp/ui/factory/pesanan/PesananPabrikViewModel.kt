package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.PesananMasukPabrikUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.seconds

class PesananPabrikViewModel(val pesananMasukUseCase: PesananMasukPabrikUseCase) : ViewModel() {

    fun getPesananMasuk() =
        pesananMasukUseCase.invoke().cachedIn(viewModelScope).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(0.5.seconds), PagingData.empty()
        )
}