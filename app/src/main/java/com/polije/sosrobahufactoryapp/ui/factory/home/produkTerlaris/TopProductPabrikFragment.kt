package com.polije.sosrobahufactoryapp.ui.factory.home.produkTerlaris

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentTopProductBinding
import com.polije.sosrobahufactoryapp.data.model.pabrik.TopSellingProduct
import com.polije.sosrobahufactoryapp.ui.factory.home.component.TopSellingProductPabrikAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopProductPabrikFragment : Fragment() {

    private lateinit var binding: FragmentTopProductBinding
    private lateinit var adapter: TopSellingProductPabrikAdapter
    private  val viewModel: TopProductPabrikViewModel  by viewModel()
    private val productList = mutableListOf<TopSellingProduct>()

    private val args: TopProductPabrikFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TopSellingProductPabrikAdapter()
        binding.recyclerViewTopProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTopProduct.adapter = adapter
        adapter.submitList(args.listTopProduct.listTopSellingProduct
        )

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
