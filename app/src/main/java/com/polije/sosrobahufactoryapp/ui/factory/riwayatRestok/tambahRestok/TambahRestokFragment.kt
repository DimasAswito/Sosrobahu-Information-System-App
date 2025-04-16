package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentTambahRestokBinding
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.TambahRestokAdapter
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.TambahRestokAdapter.OnQuantityChangeListener
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.SelectedProdukRestok
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TambahRestokFragment : Fragment() {

    private lateinit var tambahRestokAdapter: TambahRestokAdapter
    private var _binding: FragmentTambahRestokBinding? = null
    private val binding get() = _binding!!

    private val tambahRestokViewModel: TambahRestokViewModel by viewModel()

    private val args: TambahRestokFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTambahRestokBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tambahRestokViewModel.initialProdukRestock(args.listProdukTerpilih.data)
        tambahRestokAdapter = TambahRestokAdapter(object : OnQuantityChangeListener{
            override fun onQuantityChanged(
                produk: SelectedProdukRestok,
                newQty: Int
            ) {
               tambahRestokViewModel.updateQuantity(produk.item.idMasterBarang,newQty)
            }

        })
        binding.recyclerViewTambahRestok.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTambahRestok.adapter = tambahRestokAdapter

        lifecycleScope.launch {
            tambahRestokViewModel.state.collectLatest { state ->
                when (state) {
                    TambahRestokState.Failure -> {}
                    TambahRestokState.Initial -> {}
                    TambahRestokState.Loading -> {}
                    TambahRestokState.Success -> {}
                }
            }
        }

        lifecycleScope.launch {
            tambahRestokViewModel.isValid.collectLatest { isValid ->
                binding.btnTambahRestok.isEnabled = isValid
            }
        }

        lifecycleScope.launch {
            tambahRestokViewModel.produkRestock.collectLatest { data ->
                tambahRestokAdapter.submitList(data)
            }
        }


        // Tombol Tambah Restok kembali ke RiwayatFragment
        binding.btnTambahRestok.setOnClickListener {
            tambahRestokViewModel.insertRestock()
        }
    }
}

