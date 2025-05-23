package com.polije.sosrobahufactoryapp.ui.factory.hargaProduk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHargaProdukBinding

class HargaProdukPabrikFragment : Fragment() {

    private var _binding: FragmentHargaProdukBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HargaProdukPabrikViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHargaProdukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Dummy listBarangAgen produk


//        val produkPabrikAdapter = ProdukPabrikAdapter(viewModel.produkList.value)
//        binding.recyclerViewHarga.layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.recyclerViewHarga.adapter = produkPabrikAdapter

//        binding.fabTambahProduk.setOnClickListener {
//            findNavController().navigate(R.id.action_navigation_harga_to_tambahProdukFragment)
//        }

        binding.searchViewProduk.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                produkPabrikAdapter.filter(newText ?: "")
                return true
            }
        })
    }
}
