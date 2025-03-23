package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.pabrik.usecase.PesananMasukUseCase

class PesananViewModel(val pesananMasukUseCase: PesananMasukUseCase) : ViewModel() {

    fun getPesananMasuk() = pesananMasukUseCase.invoke().cachedIn(viewModelScope)
}