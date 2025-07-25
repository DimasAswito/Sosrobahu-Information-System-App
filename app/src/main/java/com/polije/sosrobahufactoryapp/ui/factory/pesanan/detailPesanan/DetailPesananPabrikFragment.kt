package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailPesananBinding
import com.polije.sosrobahufactoryapp.databinding.LoadingSuccessOverlayBinding
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.component.ItemDetailPesananPabrikAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import com.polije.sosrobahufactoryapp.utils.toTanggalIndonesia
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPesananPabrikFragment : Fragment() {

    private var _binding: FragmentDetailPesananBinding? = null
    private val binding get() = _binding!!

    private var isImageVisible = false
    private lateinit var successOverlayBinding: LoadingSuccessOverlayBinding
    private val args: DetailPesananPabrikFragmentArgs by navArgs()

    private val detailPesananViewModel: DetailPesananPabrikViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPesananBinding.inflate(inflater, container, false)
        successOverlayBinding = LoadingSuccessOverlayBinding.inflate(inflater)
        (binding.root as ViewGroup).addView(successOverlayBinding.root)
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

        val produkAdapter = ItemDetailPesananPabrikAdapter()
        binding.rvproduk.layoutManager = LinearLayoutManager(requireContext())
        binding.rvproduk.adapter = produkAdapter

        // Set status awal sesuai listBarangAgen dari Bundle
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
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val gambar = BuildConfig.PICTURE_BASE_URL + args.detailPesanan.buktiTransfer
        Glide.with(requireContext())
            .load(gambar)
            .placeholder(R.drawable.loading_foto)
            .error(R.drawable.foto_error)
            .into(binding.imgBuktiPembayaran)

        binding.btnBuktiPembayaran.setOnClickListener {
            isImageVisible = !isImageVisible
            binding.cardBuktiPembayaran.visibility =
                if (isImageVisible) View.VISIBLE else View.GONE
        }

        binding.SimpanStatusButton.visibility =
            if ((args.detailPesanan.statusPemesanan ?: 0) == 0)
                View.VISIBLE else View.GONE

        binding.SimpanStatusButton.setOnClickListener {
            val status = binding.spinnerStatus.selectedItemPosition
            detailPesananViewModel.updateDetailPesanan(args.detailPesanan.idOrder ?: 0,status)

        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        lifecycleScope.launch {
            launch {
                detailPesananViewModel.updatePesananState.collectLatest { state ->
                    when (state) {
                        is UpdateStatusPesananPabrikState.Failure -> {
                            showToast(state.errorMessage)
                            binding.SimpanStatusButton.isEnabled = true
                        }

                        UpdateStatusPesananPabrikState.Initial -> {}
                        UpdateStatusPesananPabrikState.Loading -> {
                            binding.SimpanStatusButton.isEnabled = false
                        }

                        UpdateStatusPesananPabrikState.Success -> {
                            binding.SimpanStatusButton.isEnabled = true

                            successOverlayBinding.loadingLayoutSuccess.visibility = View.VISIBLE

                            Handler(Looper.getMainLooper()).postDelayed({
                                successOverlayBinding.loadingLayoutSuccess.visibility = View.GONE
                                findNavController().navigateUp()
                            }, 2000)
                        }
                    }
                }
            }

            launch {
                detailPesananViewModel.detailPesanan.collectLatest { state ->
                    when (state) {
                        is DetailPesananPabrikState.Failure -> {}
                        DetailPesananPabrikState.Initial -> {}
                        DetailPesananPabrikState.Loading -> {}
                        is DetailPesananPabrikState.Success -> {
                            produkAdapter.submitList(state.data.itemNota)
                        }
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(this@DetailPesananPabrikFragment.requireContext(), message, Toast.LENGTH_SHORT)
            .show()
    }
}

