package com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentPilihProdukDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.PilihProdukDistributorAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PilihProdukDistributorFragment : Fragment() {

    private var _binding: FragmentPilihProdukDistributorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PilihProdukDistributorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPilihProdukDistributorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PilihProdukDistributorAdapter()
        binding.recyclerViewPilihProduk.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPilihProduk.adapter = adapter


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                if (state.data != null) {
                    adapter.submitList(state.data.pilihBarangPabrikDistributorResponse)
                }
            }
        }
    }
}