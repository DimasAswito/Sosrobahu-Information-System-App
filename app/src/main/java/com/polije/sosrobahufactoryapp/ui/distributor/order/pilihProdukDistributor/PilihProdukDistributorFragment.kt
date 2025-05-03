package com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPabrikDistributorResponseItem
import com.polije.sosrobahufactoryapp.databinding.FragmentPilihProdukDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.PilihProdukDistributorAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable


class PilihProdukDistributorFragment : Fragment() {

    private var _binding: FragmentPilihProdukDistributorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PilihProdukDistributorViewModel by viewModel()

    private val selectedList = mutableListOf<PilihBarangPabrikDistributorResponseItem>()
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

        adapter = PilihProdukDistributorAdapter(
            produkList = emptyList(),
            selectedList = selectedList,
            onItemSelected = { _, _ ->
                binding.btnPilihProduk.isEnabled = selectedList.isNotEmpty()
            }
        )

        binding.recyclerViewPilihProduk.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPilihProduk.adapter = adapter

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
                    adapter.updateList(it.pilihBarangPabrikDistributorResponse)
                }
            }
        }
    }
}

data class ProdukTerpilihDistributor(
    val data: ArrayList<PilihBarangPabrikDistributorResponseItem>
) : Serializable

