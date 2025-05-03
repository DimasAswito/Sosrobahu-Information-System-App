package com.polije.sosrobahufactoryapp.ui.distributor.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentOrderDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.dashboard.DashboardDistributorFragmentDirections
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.RiwayatOrderDistributorAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDistributorFragment : Fragment() {
    private var _binding: FragmentOrderDistributorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderDistributorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDistributorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainNavHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

        binding.fabTambahOrderDistributor.setOnClickListener {
            val action = DashboardDistributorFragmentDirections.actionDashboardDistributorFragmentToPilihProdukDistributorFragment()
            mainNavHost.navController.navigate(action)
        }




        val adapter = RiwayatOrderDistributorAdapter(object :
            RiwayatOrderDistributorAdapter.RiwayatOrderDistributorAction {
            override fun onRiwayatOrderItemClicked(idOrder: Int) {
            }
        })
        binding.recyclerViewRiwayatOrderDistributor.layoutManager =
            LinearLayoutManager(requireContext())
        binding.recyclerViewRiwayatOrderDistributor.adapter = adapter


        lifecycleScope.launch {
            viewModel.riwayatOrderDistributor.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}