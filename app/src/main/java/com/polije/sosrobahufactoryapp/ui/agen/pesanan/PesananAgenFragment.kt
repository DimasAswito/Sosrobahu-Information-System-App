package com.polije.sosrobahufactoryapp.ui.agen.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentPesananAgenBinding
import com.polije.sosrobahufactoryapp.ui.agen.pesanan.component.PesananAgenAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class PesananAgenFragment : Fragment() {

    private var _binding: FragmentPesananAgenBinding? = null
    private val binding get() = _binding!!

    private val pesananAgenViewModel: PesananAgenViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPesananAgenBinding.inflate(layoutInflater,container,false    )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainNavHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

//        val adapter = PesananAgenAdapter(object :
//            PesananAgenAdapter.PesananAgenAction {
//            override fun onItemClicked(item: PesananMasukAgenDataItem) {
//                val action =
//                    DashboardAgenFragmentDirections.actionDashboardAgenFragmentToDetailPesananAgenFragment(
//                        detailPesananAgen = item,
//                        idOrder = item.idOrder ?: 0
//                    )
//                mainNavHost.navController.navigate(action)
//            }
//
//        })
//        binding.recyclerViewPesananAgen.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerViewPesananAgen.adapter = adapter
//
//
//        lifecycleScope.launch {
//            pesananAgenViewModel.pesananMasukAgen().collectLatest { data ->
//                adapter.submitData(data)
//            }
//        }
    }
}