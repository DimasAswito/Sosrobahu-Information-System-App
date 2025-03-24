package com.polije.sosrobahufactoryapp.ui.factory.pesanan.detailPesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailPesananBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah

class DetailPesananFragment : Fragment() {

    private var _binding: FragmentDetailPesananBinding? = null
    private val binding get() = _binding!!

    private var isImageVisible = false

    val args: DetailPesananFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Ambil data dari arguments
        val distributor = arguments?.getString("distributor") ?: "Unknown"
        val tanggal = arguments?.getString("tanggal") ?: "Unknown"
        val totalHarga = arguments?.getInt("totalHarga") ?: 0
        val status = arguments?.getString("status") ?: "Unknown"

        binding.tvDistributorDetail.text = args.detailPesanan.namaDistributor
        binding.tvTanggalDetail.text = args.detailPesanan.tanggal
        binding.tvHargaTotal.text =
            getString(R.string.totalPendapatan, args.detailPesanan.total?.toRupiah())

        // Setup Spinner (Dropdown)
        val statusOptions = arrayOf("Diproses", "Selesai", "Ditolak")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            statusOptions
        )
        binding.spinnerStatus.adapter = adapter

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

        // Tombol Bukti Pembayaran
        val urlGambar = "https://example.com/bukti_pembayaran.jpg"
        Glide.with(requireContext())
            .load(urlGambar)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

