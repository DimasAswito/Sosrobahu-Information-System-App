package com.polije.sosrobahufactoryapp.ui.distributor.order.detailOrder

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
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailOrderDistributorBinding
import com.polije.sosrobahufactoryapp.databinding.LoadingPrintOverayBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.DetailOrderDistributorAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailOrderDistributorFragment : Fragment() {

    private var _binding: FragmentDetailOrderDistributorBinding? = null
    private val binding get() = _binding!!
    private var isImageVisible = false
    private lateinit var loadingPrintBinding: LoadingPrintOverayBinding
    private val args: DetailOrderDistributorFragmentArgs by navArgs()

    private val viewModel : DetailOrderDistributorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailOrderDistributorBinding.inflate(layoutInflater, container, false)
        loadingPrintBinding = LoadingPrintOverayBinding.inflate(inflater)
        (binding.root as ViewGroup).addView(loadingPrintBinding.root)
        loadingPrintBinding.root.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DetailOrderDistributorAdapter(args.detailOrder.detailProduk)
        binding.rvProdukOrder.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProdukOrder.adapter = adapter

        binding.tvTanggalOrder.text = args.detailOrder.tanggal
        binding.tvHargaTotal.text = args.detailOrder.total?.toRupiah()
        binding.tvJumlahOrder.text = getString(R.string.karton, args.detailOrder.jumlah)

        val status = when (args.detailOrder.statusPemesanan) {
            0 -> "Diproses"
            1 -> "Selesai"
            2 -> "Ditolak"
            else -> "Tidak Diketahui"
        }
        binding.tvStatusPesanan.text = status

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val gambar = BuildConfig.PICTURE_BASE_URL + args.detailOrder.buktiTransfer
        Glide.with(requireContext())
            .load(gambar)
            .placeholder(R.drawable.loading_foto)
            .error(R.drawable.foto_error)
            .into(binding.imgBuktiPembayaran)

        binding.btnCetakNota.isEnabled = args.detailOrder.statusPemesanan == 1
        binding.btnBuktiPembayaran.setOnClickListener {
            isImageVisible = !isImageVisible
            binding.cardBuktiPembayaran.visibility =
                if (isImageVisible) View.VISIBLE else View.GONE
        }

        binding.btnCetakNota.setOnClickListener {
            loadingPrintBinding.root.visibility = View.VISIBLE
            viewModel.downloadNota(args.detailOrder.idOrder ?: 0)
        }

        lifecycleScope.launch {
            launch {
                viewModel.downloadSuccess.collectLatest { success ->
                    if (success) {
                        loadingPrintBinding.root.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Nota berhasil diunduh",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

            launch {
                viewModel.downloadError.collectLatest { error ->
                    if (error != null) {
                        loadingPrintBinding.root.visibility = View.GONE
                        Toast.makeText(requireContext(), "Gagal mengunduh nota", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
    }
}