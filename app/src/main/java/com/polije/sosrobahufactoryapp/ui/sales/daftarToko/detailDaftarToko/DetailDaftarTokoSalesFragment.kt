package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.detailDaftarToko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailDaftarTokoSalesBinding
import com.polije.sosrobahufactoryapp.ui.agen.dashboard.DashboardAgenFragmentDirections
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.ItemRiwayatKunjunganAdapter
import com.polije.sosrobahufactoryapp.ui.sales.dashboard.DashboardSalesFragmentDirections

class DetailDaftarTokoSalesFragment : Fragment() {

    private var _binding: FragmentDetailDaftarTokoSalesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailDaftarTokoSalesViewModel by viewModels()

    private val args: DetailDaftarTokoSalesFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailDaftarTokoSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvRiwayatKunjunganAll.setOnClickListener {
            val action = DetailDaftarTokoSalesFragmentDirections.actionDetailTokoSalesFragmentToListRiwayatKunjunganFragment()
            findNavController().navigate(action)
        }

        val adapter = ItemRiwayatKunjunganAdapter(args.detailToko.kunjunganToko)
        binding.rvRiwayatKunjungan.layoutManager = LinearLayoutManager(context)
        binding.rvRiwayatKunjungan.adapter = adapter


        binding.tvAlamat.text = args.detailToko.lokasi
        binding.tvNoTelepon.text = args.detailToko.noTelp
        binding.tvTokoDetail.text = args.detailToko.namaToko

    }
}