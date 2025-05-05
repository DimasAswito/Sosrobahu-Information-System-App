package com.polije.sosrobahufactoryapp.ui.distributor.order.detailOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailOrderDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.DetailOrderDistributorAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailOrderDistributorFragment : Fragment() {


    private var _binding: FragmentDetailOrderDistributorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailOrderDistributorViewModel by viewModel()

    private val args: DetailOrderDistributorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailOrderDistributorBinding.inflate(layoutInflater, container, false)
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
        binding.tvStatusPesanan.text = (args.detailOrder.statusPemesanan ?: 0).toString()


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.btnBuktiPembayaran.setOnClickListener {
            Toast.makeText(requireContext(), "Mencetak riwayat...", Toast.LENGTH_SHORT).show()
        }
    }
}