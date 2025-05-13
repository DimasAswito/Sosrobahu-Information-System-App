package com.polije.sosrobahufactoryapp.ui.agen.pesanan.detailPesanan

import android.os.Bundle
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
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailPesananAgenBinding
import com.polije.sosrobahufactoryapp.ui.agen.pesanan.component.ItemDetailPesananAgenAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPesananAgenFragment : Fragment() {

    private var _binding: FragmentDetailPesananAgenBinding? = null
    private val binding get() = _binding!!
    private var isImageVisible = false
    private val viewModel: DetailPesananAgenViewModel by viewModel()
    private val args: DetailPesananAgenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPesananAgenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDetailPesananMasuk(args.idOrder)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = ItemDetailPesananAgenAdapter()
        binding.rvproduk.layoutManager = LinearLayoutManager(requireContext())
        binding.rvproduk.adapter = adapter

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.progressBar5.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                if (state.data != null) {

                    binding.tvTanggalDetail.text = state.data.dataDetail.tanggal
                    binding.tvHargaTotal.text = state.data.dataDetail.totalHarga.toRupiah()
                    binding.tvSalesDetail.text = state.data.dataDetail.namaSales
                    adapter.submitList(state.data.dataDetail.itemNota)


                }

                if (state.isSubmitted) {
                    findNavController().navigateUp()
                }
            }
        }

        binding.CetakLaporanButton.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Cetak Belum Tersedia", Toast.LENGTH_SHORT)
                .show()
        }

        // Setup Spinner (Dropdown)
        val statusOptions = arrayOf("Diproses", "Selesai", "Ditolak")
        val adapterstatus = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            statusOptions
        )
        binding.spinnerStatus.adapter = adapterstatus

        // Set status awal sesuai listBarangAgen dari Bundle
        binding.spinnerStatus.setSelection(
            (args.pesananAgenItem.statusPemesanan ?: 0)
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

        binding.SimpanStatusButton.visibility =
            if ((args.pesananAgenItem.statusPemesanan ?: 0) == 0)
                View.VISIBLE else View.GONE

        binding.SimpanStatusButton.setOnClickListener {
            val selectedStatusIndex = binding.spinnerStatus.selectedItemPosition
            viewModel.updateStatusPesananMasuk(
                idOrder = args.idOrder,
                status = selectedStatusIndex
            )
        }

        val gambar = BuildConfig.PICTURE_BASE_URL + args.pesananAgenItem.buktiTransfer
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

    }
}