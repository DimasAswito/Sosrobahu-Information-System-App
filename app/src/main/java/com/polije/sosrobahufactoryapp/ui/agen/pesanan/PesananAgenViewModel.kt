package com.polije.sosrobahufactoryapp.ui.agen.pesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.polije.sosrobahufactoryapp.domain.usecase.agen.PesananMasukAgenUseCase

class PesananAgenViewModel(val pesananMasukAgenUseCase: PesananMasukAgenUseCase) : ViewModel() {
    fun pesananMasukAgen() = pesananMasukAgenUseCase.invoke()
        .cachedIn(viewModelScope)
}