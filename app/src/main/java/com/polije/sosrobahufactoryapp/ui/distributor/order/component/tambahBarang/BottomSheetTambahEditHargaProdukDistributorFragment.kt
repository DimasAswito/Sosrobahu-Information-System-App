package com.polije.sosrobahufactoryapp.ui.distributor.order.component.tambahBarang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.polije.sosrobahufactoryapp.databinding.FragmentBottomSheetTambahEditHargaProdukBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.SelectedNewProdukDistributor
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.TambahEditHargaProdukDistributorAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetTambahEditHargaProdukDistributorFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetTambahEditHargaProdukBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BottomSheetTambahProdukViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentBottomSheetTambahEditHargaProdukBinding.inflate(inflater, container, false)
        return binding.root
    }


    private lateinit var selectedList: ProdukTerpilihTambahBarang
    private lateinit var adapter: TambahEditHargaProdukDistributorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTambahProduk.layoutManager = GridLayoutManager(context, 2)

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                if (state.isSubmitted == true) {
                    findNavController().previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("refresh", true)

                    findNavController().popBackStack()
                }


                if (state.listBarang.isNotEmpty()) {
                    viewModel.selectedProducts.collectLatest { selectedProduk ->
                        adapter = TambahEditHargaProdukDistributorAdapter(
                            produkList = state.listBarang,
                            selectedList = selectedProduk.toMutableList(),
                            onItemSelected = { item, _ ->

                                viewModel.toggleProdukSelection(item)
                            }
                        )
                        selectedList = ProdukTerpilihTambahBarang(
                            selectedProduk,
                        )
                        binding.rvTambahProduk.adapter = adapter
                        adapter.updateList(state.listBarang)
                        binding.btnTambahProdukBaru.isEnabled = selectedList.data.isNotEmpty()

                        binding.btnTambahProdukBaru.isEnabled = selectedProduk.isNotEmpty()
                    }
                }


            }
        }

        binding.btnTambahProdukBaru.setOnClickListener {
            viewModel.insertProdukTerbaru(selectedList.data.map { it.item.idMasterBarang })
        }
    }
}


data class ProdukTerpilihTambahBarang(
    val data: List<SelectedNewProdukDistributor>,
)