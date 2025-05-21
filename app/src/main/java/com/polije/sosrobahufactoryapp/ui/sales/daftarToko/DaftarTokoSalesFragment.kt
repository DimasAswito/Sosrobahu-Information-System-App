package com.polije.sosrobahufactoryapp.ui.sales.daftarToko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.sales.ListSalesDataItem
import com.polije.sosrobahufactoryapp.databinding.FragmentDaftarTokoSalesBinding
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.ItemDaftarTokoAdapter
import com.polije.sosrobahufactoryapp.ui.sales.dashboard.DashboardSalesFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DaftarTokoSalesFragment : Fragment() {

    private var _binding: FragmentDaftarTokoSalesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DaftarTokoSalesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaftarTokoSalesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mainNavHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

        val adapter = ItemDaftarTokoAdapter(object : ItemDaftarTokoAdapter.DaftarTokoAdapterAction {
            override fun onDaftarTokoClicked(item: ListSalesDataItem) {
                val action =
                    DashboardSalesFragmentDirections.actionDashboardSalesFragmentToDetailPesananSalesFragment(
                        item
                    )
                mainNavHost.navController.navigate(action)
            }
        })
        binding.recyclerViewDaftarToko.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewDaftarToko.adapter = adapter

        lifecycleScope.launch {
            viewModel.listToko().collectLatest {
                adapter.submitData(it)
            }
        }

        binding.fabTambahTokoSales.setOnClickListener {
            mainNavHost.navController.navigate(R.id.action_dashboardSalesFragment_to_tambahTokoFragment)
        }
    }
}