package com.polije.sosrobahufactoryapp.ui.agen.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.agen.RiwayatOrderAgenDataItem
import com.polije.sosrobahufactoryapp.databinding.FragmentOrderAgenBinding
import com.polije.sosrobahufactoryapp.ui.agen.dashboard.DashboardAgenFragmentDirections
import com.polije.sosrobahufactoryapp.ui.agen.order.component.RiwayatOrderAgenAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderAgenFragment : Fragment() {
    private var _binding: FragmentOrderAgenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderAgenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderAgenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainNavHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

        binding.fabTambahOrderAgen.setOnClickListener {
            val action =
                DashboardAgenFragmentDirections.actionDashboardAgenFragmentToPilihProdukAgenFragment()
            mainNavHost.navController.navigate(action)
        }

        binding.btnEditHarga.setOnClickListener {
            val action =
                DashboardAgenFragmentDirections.actionDashboardAgenFragmentToPengaturanHargaAgenFragment()
            mainNavHost.navController.navigate(action)
        }

        val adapter = RiwayatOrderAgenAdapter(object :
            RiwayatOrderAgenAdapter.RiwayatOrderAgenAction {
            override fun onRiwayatOrderItemClicked(order: RiwayatOrderAgenDataItem) {
                val action =
                    DashboardAgenFragmentDirections.actionDashboardAgenFragmentToDetailOrderAgenFragment(
                        order
                    )
                mainNavHost.navController.navigate(action)
            }
        })

        binding.recyclerViewRiwayatOrderAgen.layoutManager =
            LinearLayoutManager(requireContext())
        binding.recyclerViewRiwayatOrderAgen.adapter = adapter


        lifecycleScope.launch {
            viewModel.getOrderAgen().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}