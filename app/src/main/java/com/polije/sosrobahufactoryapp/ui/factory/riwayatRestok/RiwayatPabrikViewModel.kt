package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.pabrik.RiwayatRestokUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class RiwayatPabrikViewModel(val riwayatRestokUseCase: RiwayatRestokUseCase) : ViewModel() {
    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val riwayatRestock = searchQuery.debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            riwayatRestokUseCase.invoke(query).cachedIn(viewModelScope)
        }


    fun onSeachQueryChanged(query: String) {
        searchQuery.value = query
    }

}