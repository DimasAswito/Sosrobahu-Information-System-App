package com.polije.sosrobahufactoryapp.ui.distributor.order.ubahHarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.distributor.DistributorBarangItems
import com.polije.sosrobahufactoryapp.databinding.FragmentPengaturanHargaDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.PilihProdukEditHargaAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PengaturanHargaDistributorFragment : Fragment() {

    private var _binding: FragmentPengaturanHargaDistributorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengaturanHargaDistributorViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPengaturanHargaDistributorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            PilihProdukEditHargaAdapter(object : PilihProdukEditHargaAdapter.PengaturanHargaAction {
                override fun onPengaturanHargaItemClicked(item: DistributorBarangItems) {
                    val action =
                        PengaturanHargaDistributorFragmentDirections.actionPengaturanHargaDistributorFragmentToBottomsheetEditHargaDistributorFragment(
                            item
                        )
                    findNavController().navigate(action)
                }
            })

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvHarga.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.fabTambahProdukEditHarga.setOnClickListener {
            findNavController().navigate(R.id.action_pengaturanHargaDistributorFragment_to_bottomSheetTambahEditHargaProdukDistributorFragment)
        }





        lifecycleScope.launch {
            launch {
                viewModel.state.collectLatest { state ->
                    if (state.data.isNotEmpty()) {
                        adapter.submitList(state.data)
                    }
                }
            }

        }


        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>("refresh")
            ?.observe(viewLifecycleOwner) { needsRefresh ->
                if (needsRefresh == true) {
                    viewModel.initialLoad()
                    findNavController().currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<Boolean>("refresh")
                }
            }
    }


    companion object {

    }
}