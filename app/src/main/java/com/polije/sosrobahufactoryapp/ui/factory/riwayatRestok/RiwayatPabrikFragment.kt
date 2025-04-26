package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.pabrik.RiwayatRestockItem
import com.polije.sosrobahufactoryapp.databinding.FragmentRiwayatBinding
import com.polije.sosrobahufactoryapp.ui.factory.dashboard.DashboardPabrikFragmentDirections
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.RiwayatRestokPabrikAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RiwayatPabrikFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var riwayatAdapter: RiwayatRestokPabrikAdapter

    private val riwayatPabrikViewModel: RiwayatPabrikViewModel by viewModel()

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewRiwayat)
        val fabTambahRestok: FloatingActionButton = binding.fabTambahRestok

        // Dummy Data
//        riwayatList = mutableListOf(
//            restock_pabrik("Produk A", "10 Februari 2025", 50, R.drawable.logo),
//            restock_pabrik("Produk B", "8 Februari 2025", 30, R.drawable.logo),
//            restock_pabrik("Produk C", "5 Februari 2025", 70, R.drawable.logo),
//            restock_pabrik("Produk D", "3 Februari 2025", 40, R.drawable.logo)
//        )


        riwayatAdapter =
            RiwayatRestokPabrikAdapter(object : RiwayatRestokPabrikAdapter.OnRiwayatItemClicked {
                override fun onItemClick(item: RiwayatRestockItem) {
                    val action =
                        DashboardPabrikFragmentDirections.actionDashboardFragmentToDetailRestokFragment(
                            item
                        )
                    findNavController().navigate(action)
                }
            })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = riwayatAdapter

        lifecycleScope.launch {
            riwayatPabrikViewModel.riwayatRestock.collectLatest { item ->
                riwayatAdapter.submitData(item)
            }
        }

        fabTambahRestok.setOnClickListener {
            val action =
                DashboardPabrikFragmentDirections.actionDashboardFragmentToPilihProdukRestokFragment()
            requireActivity().findNavController(R.id.fragmentContainerView).navigate(action)

        }

    }
}
