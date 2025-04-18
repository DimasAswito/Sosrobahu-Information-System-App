package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailPesananBinding
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.component.DetailPesananItemAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import com.polije.sosrobahufactoryapp.utils.toTanggalIndonesia
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPesananFragment : Fragment() {

    private var _binding: FragmentDetailPesananBinding? = null
    private val binding get() = _binding!!

    private var isImageVisible = false

    private val args: DetailPesananFragmentArgs by navArgs()

    private val detailPesananViewModel: DetailPesananViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailPesananViewModel.detailPesananMasuk(args.detailPesanan.idOrder ?: 0)
        binding.tvDistributorDetail.text = args.detailPesanan.namaDistributor
        binding.tvTanggalDetail.text = args.detailPesanan.tanggal?.toTanggalIndonesia()
        binding.tvHargaTotal.text = args.detailPesanan.total?.toRupiah()


        // Setup Spinner (Dropdown)
        val statusOptions = arrayOf("Diproses", "Selesai", "Ditolak")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            statusOptions
        )
        binding.spinnerStatus.adapter = adapter

        val produkAdapter = DetailPesananItemAdapter()
        binding.rvproduk.layoutManager = LinearLayoutManager(requireContext())
        binding.rvproduk.adapter = produkAdapter


        // Set status awal sesuai data dari Bundle
        binding.spinnerStatus.setSelection(
            (args.detailPesanan.statusPemesanan ?: 0)
        )

        // Event Listener saat user memilih status baru
        binding.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedStatus = statusOptions[position]
                Toast.makeText(
                    requireContext(),
                    "Status diubah ke: $selectedStatus",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        val gambar = BuildConfig.PICTURE_BASE_URL + args.detailPesanan.buktiTransfer
        Glide.with(requireContext())
            .load(gambar)
            .placeholder(R.drawable.logo) // Gambar sementara
            .error(R.drawable.logo) // Gambar jika gagal
            .into(binding.imgBuktiPembayaran)

        binding.btnBuktiPembayaran.setOnClickListener {
            isImageVisible = !isImageVisible
            binding.imgBuktiPembayaran.visibility = if (isImageVisible) View.VISIBLE else View.GONE
        }

        binding.CetakLaporanButton.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Cetak Belum Tersedia", Toast.LENGTH_SHORT)
                .show()
        }

        lifecycleScope.launch {

            detailPesananViewModel.detailPesanan.collectLatest { state->
                when (state){
                    is DetailPesananState.Failure -> {}
                    DetailPesananState.Initial -> {}
                    DetailPesananState.Loading -> {}
                    is DetailPesananState.Success -> {
                        produkAdapter.submitList(state.data.itemNota)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

