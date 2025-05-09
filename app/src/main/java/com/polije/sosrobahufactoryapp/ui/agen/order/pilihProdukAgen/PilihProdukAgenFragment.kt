package com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentPilihProdukAgenBinding
import com.polije.sosrobahufactoryapp.ui.agen.order.component.PilihProdukAgenAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

class PilihProdukAgenFragment : Fragment() {

    private var _binding: FragmentPilihProdukAgenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PilihProdukAgenViewModel by viewModel()

    private lateinit var selectedList: ProdukTerpilihAgen
    private lateinit var adapter: PilihProdukAgenAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPilihProdukAgenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.recyclerViewPilihProduk.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                state.data?.let {
                    viewModel.selectedProducts.collectLatest { selectedProduk ->
                        adapter = PilihProdukAgenAdapter(
                            produkList = state.data.barangDistributor,
                            selectedList = selectedProduk.toMutableList(),
                            onItemSelected = { item, _ ->

                                viewModel.toggleProdukSelection(item)
                            }
                        )
                        selectedList = ProdukTerpilihAgen(
                            selectedProduk,
                            namaLengkap = state.data.distributor.namaLengkap.toString(),
                            namaBank = state.data.distributor.namaBank.toString(),
                            norek = state.data.distributor.noRek.toInt()
                        )
                        binding.recyclerViewPilihProduk.adapter = adapter
                        adapter.updateList(it.barangDistributor)
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
                PilihProdukAgenFragmentDirections.actionPilihProdukAgenFragmentToTambahOrderAgenFragment(
                    selectedList
                )
            findNavController().navigate(action)
        }
    }
}

@Parcelize
data class ProdukTerpilihAgen(
    val data: List<SelectedProdukAgen>,
    val namaLengkap: String,
    val namaBank: String,
    val norek: Int
) : Parcelable
