package com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentPilihProdukDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.PilihProdukDistributorAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel


class PilihProdukDistributorFragment : Fragment() {

    private var _binding: FragmentPilihProdukDistributorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PilihProdukDistributorViewModel by viewModel()

    private lateinit var selectedList: ProdukTerpilihDistributor
    private lateinit var adapter: PilihProdukDistributorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPilihProdukDistributorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.recyclerViewPilihProduk.layoutManager = LinearLayoutManager(requireContext())


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

//        binding.btnPilihProduk.setOnClickListener {
//            val produkTerpilih = ProdukTerpilihDistributor(ArrayList(selectedList))
//            val action = PilihProdukDistributorFragmentDirections
//                .actionPilihProdukDistributorFragmentToTambahOrderDistributorFragment(produkTerpilih)
//            findNavController().navigate(action)
//        }

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                state.data?.let {
                    viewModel.selectedProducts.collectLatest { selectedProduk ->
                        adapter = PilihProdukDistributorAdapter(
                            produkList = state.data.pilihBarangPabrikDistributorResponse,
                            selectedList = selectedProduk.toMutableList(),
                            onItemSelected = { item, _ ->

                                viewModel.toggleProdukSelection(item)
                            }
                        )
                        selectedList = ProdukTerpilihDistributor(
                            selectedProduk,
                            namaLengkap = state.data.bankPabrikResponse?.namaLengkap.toString(),
                            namaBank = state.data.bankPabrikResponse?.namaBank.toString(),
                            norek = state.data.bankPabrikResponse?.norek ?: 0
                        )
                        binding.recyclerViewPilihProduk.adapter = adapter
                        adapter.updateList(it.pilihBarangPabrikDistributorResponse)
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
                PilihProdukDistributorFragmentDirections.actionPilihProdukDistributorFragmentToTambahOrderDistributorFragment(
                    selectedList
                )
            findNavController().navigate(action)
        }
    }
}

@Parcelize
data class ProdukTerpilihDistributor(
    val data: List<SelectedProdukDistributor>,
    val namaLengkap: String,
    val namaBank: String,
    val norek: Int
) : Parcelable

