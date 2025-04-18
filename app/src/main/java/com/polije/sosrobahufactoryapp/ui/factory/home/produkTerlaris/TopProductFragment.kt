package com.polije.sosrobahufactoryapp.ui.factory.home.produkTerlaris

import DashboardPabrikResponse
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentTopProductBinding
import com.polije.sosrobahufactoryapp.data.model.TopSellingProduct
import com.polije.sosrobahufactoryapp.ui.factory.home.component.TopSellingProductAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopProductFragment : Fragment() {

    private lateinit var binding: FragmentTopProductBinding
    private lateinit var adapter: TopSellingProductAdapter
    private  val viewModel: TopProductViewModel  by viewModel()
    private val productList = mutableListOf<TopSellingProduct>()

    private val args: TopProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TopSellingProductAdapter()
        binding.recyclerViewTopProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTopProduct.adapter = adapter
        adapter.submitList(args.listTopProduct.listTopSellingProduct
        )

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
