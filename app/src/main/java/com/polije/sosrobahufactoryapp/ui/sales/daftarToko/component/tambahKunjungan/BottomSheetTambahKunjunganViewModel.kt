package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.tambahKunjungan

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.domain.usecase.sales.TambahKunjunganTokoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BottomSheetTambahKunjunganViewModel(private val tambahKunjunganTokoUseCase: TambahKunjunganTokoUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(BottomSheetTambahKunjunganState())
    val state = _state.asStateFlow()

    val isValid = _state.map { state ->
        !_state.value.tanggal.isNullOrBlank() && _state.value.sisaProduk != null && _state.value.buktiKunjungan != Uri.EMPTY
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun tambahKunjunganToko(idToko: Int) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    tambahKunjunganTokoUseCase.invoke(
                        idToko,
                        _state.value.tanggal.toString(),
                        _state.value.sisaProduk ?: 0,
                        _state.value.buktiKunjungan
                    )
                }
            }.onSuccess {
                _state.update { it.copy(isSubmitted = true) }
            }.onFailure {
                _state.update { it.copy(isSubmitted = false) }
            }
        }
    }

    fun updateTanggal(value : String) = _state.update { it.copy(tanggal = value) }
    fun updateSisaProduk(value : Int) = _state.update { it.copy(sisaProduk = value) }
    fun updateGambar(value : Uri) = _state.update{ it.copy(buktiKunjungan = value)}
}