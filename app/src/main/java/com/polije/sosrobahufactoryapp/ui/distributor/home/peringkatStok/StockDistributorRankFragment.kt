package com.polije.sosrobahufactoryapp.ui.distributor.home.peringkatStok

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.pabrik.TopSellingProduct
import com.polije.sosrobahufactoryapp.databinding.FragmentStockDistributorRankBinding
import com.polije.sosrobahufactoryapp.databinding.FragmentTopProductBinding
import com.polije.sosrobahufactoryapp.ui.distributor.home.component.TopStockDistributorAdapter
import com.polije.sosrobahufactoryapp.ui.factory.home.component.TopSellingProductPabrikAdapter
import com.polije.sosrobahufactoryapp.ui.factory.home.produkTerlaris.TopProductPabrikFragmentArgs
import kotlin.getValue

class StockDistributorRankFragment : Fragment() {

    private lateinit var binding: FragmentStockDistributorRankBinding
    private lateinit var adapter: TopStockDistributorAdapter
    private val viewModel: StockDistributorRankViewModel by viewModels()
    private val productList = mutableListOf<TopSellingProduct>()

//    private val args: StockDistributorRankFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockDistributorRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TopStockDistributorAdapter()
        binding.recyclerViewTopProduct.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerViewTopProduct.adapter = adapter
//        adapter.submitList(args.listTopProduct.listTopSellingProduct
//        )

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}