package com.polije.sosrobahufactoryapp.ui.agen.pesanan.detailPesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailPesananAgenBinding
import kotlin.getValue

class DetailPesananAgenFragment : Fragment() {

    private var _binding: FragmentDetailPesananAgenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailPesananAgenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPesananAgenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.CetakLaporanButton.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Cetak Belum Tersedia", Toast.LENGTH_SHORT)
                .show()
        }
    }
}