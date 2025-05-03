package com.polije.sosrobahufactoryapp.ui.distributor.pesanan.detailPesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailPesananDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.pesanan.component.ItemDetailPesananDistributorAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPesananDistributorFragment : Fragment() {

    private var _binding: FragmentDetailPesananDistributorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailPesananDistributorViewModel by viewModel()
    private val args: DetailPesananDistributorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPesananDistributorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDetailPesananMasuk(args.idOrder)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        val adapter = ItemDetailPesananDistributorAdapter()
        binding.rvproduk.layoutManager = LinearLayoutManager(requireContext())
        binding.rvproduk.adapter = adapter

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.progressBar5.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                if (state.data != null) {
                    with(binding) {
                        tvTanggalDetail.text = state.data.tanggal
                        tvHargaTotal.text = state.data.totalHarga?.toRupiah()
                        tvAgenDetail.text = state.data.namaAgen
                        adapter.submitList(state.data.itemNota)
                    }
                }
            }
        }

        binding.CetakLaporanButton.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Cetak Belum Tersedia", Toast.LENGTH_SHORT)
                .show()
        }
    }
}