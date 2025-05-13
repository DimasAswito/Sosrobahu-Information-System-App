package com.polije.sosrobahufactoryapp.ui.sales.order.detailOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailOrderSalesBinding
import com.polije.sosrobahufactoryapp.ui.sales.order.component.DetailOrderSalesAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah

class DetailOrderSalesFragment : Fragment() {

    private var _binding: FragmentDetailOrderSalesBinding? = null
    private val binding get() = _binding!!

    private val args: DetailOrderSalesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailOrderSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DetailOrderSalesAdapter(args.listOrderSales.detailProduk)

        binding.rvProdukOrder.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvTotal.text = args.listOrderSales.total?.toRupiah()
        binding.tvTanggalOrder.text = args.listOrderSales.tanggal

        binding.tvJumlahOrder.text = getString(R.string.slop, args.listOrderSales.jumlah)

        val status = when (args.listOrderSales.statusPemesanan) {
            0 -> "Diproses"
            1 -> "Selesai"
            2 -> "Ditolak"
            else -> "Tidak Diketahui"
        }
        binding.tvStatusPesanan.text = status


    }
}