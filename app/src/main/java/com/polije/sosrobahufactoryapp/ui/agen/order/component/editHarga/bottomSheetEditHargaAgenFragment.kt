package com.polije.sosrobahufactoryapp.ui.agen.order.component.editHarga

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polije.sosrobahufactoryapp.R

class bottomSheetEditHargaAgenFragment : Fragment() {

    companion object {
        fun newInstance() = bottomSheetEditHargaAgenFragment()
    }

    private val viewModel: BottomSheetEditHargaAgenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_bottom_sheet_edit_harga_agen, container, false)
    }
}