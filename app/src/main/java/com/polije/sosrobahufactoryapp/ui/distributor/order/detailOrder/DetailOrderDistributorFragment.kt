package com.polije.sosrobahufactoryapp.ui.distributor.order.detailOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailPesananDistributorBinding

class DetailOrderDistributorFragment : Fragment() {


    private var _binding: FragmentDetailPesananDistributorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailOrderDistributorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPesananDistributorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.btnBuktiPembayaran.setOnClickListener {
            Toast.makeText(requireContext(), "Mencetak riwayat...", Toast.LENGTH_SHORT).show()
        }
    }
}