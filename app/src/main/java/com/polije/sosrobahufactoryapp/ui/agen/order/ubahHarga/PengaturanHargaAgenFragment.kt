package com.polije.sosrobahufactoryapp.ui.agen.order.ubahHarga

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentPengaturanHargaAgenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PengaturanHargaAgenFragment : Fragment() {

    private var _binding: FragmentPengaturanHargaAgenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengaturanHargaAgenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPengaturanHargaAgenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabTambahProdukEditHarga.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardAgenFragment_to_pengaturanHargaAgenFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.fabTambahProdukEditHarga.setOnClickListener {
            findNavController().navigate(R.id.action_pengaturanHargaAgenFragment_to_bottomSheetTambahEditHargaProdukAgenFragment)
        }
    }
}