package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentPilihProdukRestokBinding
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.PilihProdukPabrikAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

class PilihProdukRestokFragment : Fragment() {

    private lateinit var adapter: PilihProdukPabrikAdapter
    private lateinit var produkTerpilih: ProdukTerpilih

    private var _binding: FragmentPilihProdukRestokBinding? = null
    private val binding get() = _binding!!

    private val produkRestokViewModel: ProdukRestokViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPilihProdukRestokBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        lifecycleScope.launch {
            produkRestokViewModel.productsState.collectLatest { state ->
                when (state) {
                    is PilihProdukRestockPabrikState.Success -> {
                        produkRestokViewModel.selectedProducts.collectLatest { selected ->
                            adapter = PilihProdukPabrikAdapter(
                                produkList = state.data,
                                selectedList = selected.toMutableList(),
                                onItemSelected = { produk, isSelected ->
                                    produkRestokViewModel.toggleProdukSelection(
                                        produk
                                    )
                                }
                            )
                            produkTerpilih = ProdukTerpilih(data = selected)
                            binding.recyclerViewPilihProduk.adapter = adapter
                            binding.btnPilihProduk.isEnabled = selected.isNotEmpty()
                        }
                    }

                    is PilihProdukRestockPabrikState.Loading -> { /* show loading */
                    }

                    is PilihProdukRestockPabrikState.Error -> { /* show error */
                    }
                }
            }
        }

        binding.recyclerViewPilihProduk.layoutManager = GridLayoutManager(requireContext(), 2)


        binding.btnPilihProduk.setOnClickListener {
            val action =
                PilihProdukRestokFragmentDirections.actionPilihProdukRestokFragmentToTambahRestokFragment(
                    produkTerpilih
                )
            findNavController().navigate(
                action
            )
        }
    }
}

@Parcelize
data class ProdukTerpilih(val data: List<SelectedProdukRestok>) : Parcelable
