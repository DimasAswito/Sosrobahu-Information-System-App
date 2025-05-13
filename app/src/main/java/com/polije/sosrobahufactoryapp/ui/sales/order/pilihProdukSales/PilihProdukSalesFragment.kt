package com.polije.sosrobahufactoryapp.ui.sales.order.pilihProdukSales

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentPilihProdukSalesBinding
import com.polije.sosrobahufactoryapp.ui.sales.order.component.PilihProdukSalesAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

class PilihProdukSalesFragment : Fragment() {

    private var _binding: FragmentPilihProdukSalesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PilihProdukSalesViewModel by viewModel()
    private lateinit var selectedList: ProdukTerpilihSales
    private lateinit var adapter: PilihProdukSalesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPilihProdukSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.recyclerViewPilihProduk.layoutManager = GridLayoutManager(requireContext(), 2)
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                state.data?.let {
                    viewModel.selectedProducts.collectLatest { selectedProduk ->
                        adapter = PilihProdukSalesAdapter(
                            produkList = state.data.listBarangAgen,
                            selectedList = selectedProduk.toMutableList(),
                            onItemSelected = { item, _ ->

                                viewModel.toggleProdukSelection(item)
                            }
                        )
                        selectedList = ProdukTerpilihSales(
                            selectedProduk,
                            state.data.transfer.namaLengkap,
                            state.data.transfer.namaBank,
                            Integer.getInteger(state.data.transfer.norek,0)
                            )
                        binding.recyclerViewPilihProduk.adapter = adapter
                        adapter.updateList(it.listBarangAgen)
                        binding.btnPilihProduk.isEnabled = selectedList.data.isNotEmpty()
                    }
                }

                state.errorMessage?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnPilihProduk.setOnClickListener {
            val action =
                PilihProdukSalesFragmentDirections.actionPilihProdukSalesFragmentToTambahOrderSalesFragment(
                    selectedList
                )
            findNavController().navigate(action)
        }
    }
}

@Parcelize
data class ProdukTerpilihSales(
    val data: List<SelectedProdukSales>,
    val namaLengkap: String,
    val namaBank: String,
    val norek: Int
) : Parcelable

