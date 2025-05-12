package com.polije.sosrobahufactoryapp.ui.sales.order.detailOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
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

        binding.rvListProduk.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.txtJumlah.text = args.listOrderSales.total?.toRupiah()
        binding.txtTitle.text = args.listOrderSales.tanggal

    }
}