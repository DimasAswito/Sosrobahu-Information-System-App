package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentTambahRestokBinding
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.TambahRestokAdapter
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.PilihProdukRestokFragmentDirections
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.ProdukRestokViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TambahRestokFragment : Fragment() {

    private lateinit var tambahRestokAdapter: TambahRestokAdapter
    private var _binding: FragmentTambahRestokBinding? = null
    private val binding get() = _binding!!

    private val args : TambahRestokFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTambahRestokBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tambahRestokAdapter = TambahRestokAdapter()
        binding.recyclerViewTambahRestok.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTambahRestok.adapter = tambahRestokAdapter

        tambahRestokAdapter.submitList(args.listProdukTerpilih.data)


        // Tombol Tambah Restok kembali ke RiwayatFragment
        binding.btnTambahRestok.setOnClickListener {


            findNavController().navigate(R.id.action_tambahRestokFragment_to_navigation_riwayat)
        }
    }
}

