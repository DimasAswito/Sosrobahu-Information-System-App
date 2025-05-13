package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.listRiwayatKunjungan

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.R

class ListRiwayatKunjunganFragment : Fragment() {

    companion object {
        fun newInstance() = ListRiwayatKunjunganFragment()
    }

    private val viewModel: ListRiwayatKunjunganViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_list_riwayat_kunjungan, container, false)

//        binding.btnBack.setOnClickListener {
//            findNavController().navigateUp()
//        }
    }
}