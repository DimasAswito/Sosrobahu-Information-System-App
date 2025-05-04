package com.polije.sosrobahufactoryapp.ui.distributor.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorDataItem
import com.polije.sosrobahufactoryapp.databinding.FragmentPesananDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.dashboard.DashboardDistributorFragmentDirections
import com.polije.sosrobahufactoryapp.ui.distributor.pesanan.component.PesananDistributorAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PesananDistributorFragment : Fragment() {

    private var _binding: FragmentPesananDistributorBinding? = null
    private val binding get() = _binding!!

    private val pesananDistributorViewModel: PesananDistributorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPesananDistributorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainNavHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

        val adapter = PesananDistributorAdapter(object :
            PesananDistributorAdapter.PesananDistributorAction {
            override fun onItemClicked(item: PesananMasukDistributorDataItem) {
                val action = DashboardDistributorFragmentDirections.actionDashboardDistributorFragmentToDetailPesananDistributorFragment(item)
                mainNavHost.navController.navigate(action)
            }

        })
        binding.recyclerViewPesanan.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPesanan.adapter = adapter


        lifecycleScope.launch {
            pesananDistributorViewModel.pesananMasukDistributor().collectLatest { data ->
                adapter.submitData(data)
            }
        }
    }
}