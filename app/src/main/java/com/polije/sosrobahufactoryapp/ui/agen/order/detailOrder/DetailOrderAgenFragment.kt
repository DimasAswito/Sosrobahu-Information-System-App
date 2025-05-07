package com.polije.sosrobahufactoryapp.ui.agen.order.detailOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailOrderAgenBinding
import com.polije.sosrobahufactoryapp.ui.agen.order.component.DetailOrderAgenAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class DetailOrderAgenFragment : Fragment() {

    private var _binding: FragmentDetailOrderAgenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailOrderAgenViewModel by viewModel()
    private var isImageVisible = false
//    private val args: DetailOrderAgenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailOrderAgenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val adapter = DetailOrderAgenAdapter(args.detailOrder.detailProduk)
//        binding.rvProdukOrder.layoutManager = LinearLayoutManager(requireContext())
//        binding.rvProdukOrder.adapter = adapter
//
//        binding.tvTanggalOrder.text = args.detailOrder.tanggal
//        binding.tvHargaTotal.text = args.detailOrder.total?.toRupiah()
//        binding.tvJumlahOrder.text = getString(R.string.karton, args.detailOrder.jumlah)
//
//        val status = when (args.detailOrder.statusPemesanan) {
//            0 -> "Diproses"
//            1 -> "Selesai"
//            2 -> "Ditolak"
//            else -> "Tidak Diketahui"
//        }
//        binding.tvStatusPesanan.text = status
//
//        binding.btnBack.setOnClickListener {
//            findNavController().navigateUp()
//        }
//
//        val gambar = BuildConfig.PICTURE_BASE_URL + args.detailOrder.buktiTransfer
//        Glide.with(requireContext())
//            .load(gambar)
//            .placeholder(R.drawable.loading_foto)
//            .error(R.drawable.foto_error)
//            .into(binding.imgBuktiPembayaran)
//
//        binding.btnCetakNota.isEnabled = args.detailOrder.statusPemesanan == 1
//        binding.btnBuktiPembayaran.setOnClickListener {
//            isImageVisible = !isImageVisible
//            binding.cardBuktiPembayaran.visibility =
//                if (isImageVisible) View.VISIBLE else View.GONE
//        }
    }
}