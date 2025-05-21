package com.polije.sosrobahufactoryapp.ui.sales.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.sales.OrderSalesDataItem
import com.polije.sosrobahufactoryapp.databinding.FragmentOrderSalesBinding
import com.polije.sosrobahufactoryapp.ui.sales.dashboard.DashboardSalesFragmentDirections
import com.polije.sosrobahufactoryapp.ui.sales.order.component.RiwayatOrderSalesAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderSalesFragment : Fragment() {

    private var _binding: FragmentOrderSalesBinding? = null
    private val binding get() = _binding!!


    private val viewModel: OrderSalesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainNavHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

        binding.fabTambahOrderSales.setOnClickListener {
            val action =
                DashboardSalesFragmentDirections.actionDashboardSalesFragmentToPilihProdukSalesFragment()
            mainNavHost.navController.navigate(action)
        }

        val adapter = RiwayatOrderSalesAdapter(object :
            RiwayatOrderSalesAdapter.RiwayatOrderSalesAction {
            override fun onRiwayatOrderItemClicked(order: OrderSalesDataItem) {
                val action =
                    DashboardSalesFragmentDirections.actionDashboardSalesFragmentToDetailOrderSalesFragment(
                        order
                    )
                mainNavHost.navController.navigate(action)
            }
        })

        binding.recyclerViewRiwayatOrderSales.layoutManager =
            LinearLayoutManager(requireContext())
        binding.recyclerViewRiwayatOrderSales.adapter = adapter


        lifecycleScope.launch {
            viewModel.orderSalesAgen().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}