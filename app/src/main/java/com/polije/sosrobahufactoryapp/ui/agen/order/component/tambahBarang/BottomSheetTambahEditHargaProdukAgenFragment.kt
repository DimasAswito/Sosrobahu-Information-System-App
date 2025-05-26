package com.polije.sosrobahufactoryapp.ui.agen.order.component.tambahBarang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.polije.sosrobahufactoryapp.databinding.FragmentBottomSheetTambahEditHargaProdukAgenBinding
import com.polije.sosrobahufactoryapp.ui.agen.order.component.SelectedNewProdukAgen
import com.polije.sosrobahufactoryapp.ui.agen.order.component.TambahEditHargaProdukAgenAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetTambahEditHargaProdukAgenFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetTambahEditHargaProdukAgenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BottomSheetTambahEditHargaProdukAgenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentBottomSheetTambahEditHargaProdukAgenBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var selectedList: ProdukTerpilihTambahBarangAgen
    private lateinit var adapter: TambahEditHargaProdukAgenAdapter

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
                        adapter = TambahEditHargaProdukAgenAdapter(
                            produkList = state.listBarang,
                            selectedList = selectedProduk.toMutableList(),
                            onItemSelected = { item, _ ->

                                viewModel.toggleProdukSelection(item)
                            }
                        )
                        selectedList = ProdukTerpilihTambahBarangAgen(
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


data class ProdukTerpilihTambahBarangAgen(
    val data: List<SelectedNewProdukAgen>,
)