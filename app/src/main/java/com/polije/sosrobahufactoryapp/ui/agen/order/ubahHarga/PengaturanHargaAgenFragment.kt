package com.polije.sosrobahufactoryapp.ui.agen.order.ubahHarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.agen.RokokAgensItem
import com.polije.sosrobahufactoryapp.databinding.FragmentPengaturanHargaAgenBinding
import com.polije.sosrobahufactoryapp.ui.agen.order.component.PilihProdukEditHargaAgenAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PengaturanHargaAgenFragment : Fragment() {

    private var _binding: FragmentPengaturanHargaAgenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengaturanHargaAgenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPengaturanHargaAgenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            PilihProdukEditHargaAgenAdapter(action = object :
                PilihProdukEditHargaAgenAdapter.PengaturanHargaAgenAction {
                override fun onPengaturanHargaItemClicked(item: RokokAgensItem) {
                    val action =
                        PengaturanHargaAgenFragmentDirections.actionPengaturanHargaAgenFragmentToBottomSheetEditHargaAgenFragment(
                            item
                        )
                    findNavController().navigate(action)
                }
            })
        binding.rvHarga.adapter = adapter
        binding.rvHarga.layoutManager = LinearLayoutManager(context)

        binding.fabTambahProdukEditHarga.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardAgenFragment_to_pengaturanHargaAgenFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.fabTambahProdukEditHarga.setOnClickListener {
            findNavController().navigate(R.id.action_pengaturanHargaAgenFragment_to_bottomSheetTambahEditHargaProdukAgenFragment)
        }

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                if (state.list.isNotEmpty()) {
                    adapter.submitList(state.list)
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
}