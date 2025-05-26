package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.detailRestok

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
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailRestokBinding
import com.polije.sosrobahufactoryapp.databinding.LoadingPrintOverayBinding
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.DetailRestockPabrikAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailRestokPabrikFragment : Fragment() {

    private var _binding: FragmentDetailRestokBinding? = null
    private val binding get() = _binding!!

    private val args: DetailRestokPabrikFragmentArgs by navArgs()
    private lateinit var loadingPrintBinding: LoadingPrintOverayBinding

    private val viewModel: DetailRestokPabrikViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailRestokBinding.inflate(layoutInflater, container, false)
        loadingPrintBinding = LoadingPrintOverayBinding.inflate(inflater)
        (binding.root as ViewGroup).addView(loadingPrintBinding.root)
        loadingPrintBinding.root.visibility = View.GONE // awalnya disembunyikan
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtJumlah.text = args.restockDetail.jumlah
        binding.txtJumlahKeseluruhan.text = args.restockDetail.jumlah
        binding.tvTanggalRestok.text = args.restockDetail.tanggal
//        binding.txtTitle.text = getString(
//            R.string.detail_restock,
//            args.restockDetail.tanggal.toString(),
//            "RST1234${args.restockDetail.idRestock}"
//        )

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnKembali.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = DetailRestockPabrikAdapter(args.restockDetail.detailProduk)
        binding.rvListProduk.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListProduk.adapter = adapter

        binding.btnCetak.setOnClickListener {
            loadingPrintBinding.root.visibility = View.VISIBLE
            viewModel.downloadNota(args.restockDetail.idRestock ?: 0)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
