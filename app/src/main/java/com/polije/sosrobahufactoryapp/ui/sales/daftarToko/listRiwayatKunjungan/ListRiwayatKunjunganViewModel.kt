package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.listRiwayatKunjungan

import androidx.lifecycle.ViewModel
import com.polije.sosrobahufactoryapp.domain.usecase.sales.KunjunganTokoUseCase

class ListRiwayatKunjunganViewModel(private val listKunjunganTokoUseCase: KunjunganTokoUseCase) :
    ViewModel() {
    fun getListKunjungan(idToko: Int) = listKunjunganTokoUseCase.invoke(idToko)
}