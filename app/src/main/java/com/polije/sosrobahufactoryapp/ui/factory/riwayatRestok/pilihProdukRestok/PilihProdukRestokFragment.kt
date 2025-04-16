package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.Produk
import com.polije.sosrobahufactoryapp.data.model.ProdukRestok
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.PilihProdukAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PilihProdukRestokFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PilihProdukAdapter
    private var produkTerpilih = mutableListOf<ProdukRestok>()
    private var produkList = mutableListOf<Produk>()
    private val selectedProduk = mutableListOf<Produk>()

    private val produkRestokViewModel : ProdukRestokViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pilih_produk_restok, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewPilihProduk)
        val btnPilihProduk = view.findViewById<Button>(R.id.btnPilihProduk)

      lifecycleScope.launch {
          produkRestokViewModel.productsState.collectLatest { state ->
              when (state) {
                  is PilihProdukRestockState.Success -> {
                      produkRestokViewModel.selectedProducts.collect { selected ->
                          adapter = PilihProdukAdapter(
                              produkList = state.data,
                              selectedList = selected.toMutableList(),
                              onItemSelected = { item -> produkRestokViewModel.toggleProdukSelection(item) }
                          )
                          recyclerView.adapter = adapter
                      }
                  }
                  is PilihProdukRestockState.Loading -> { /* show loading */ }
                  is PilihProdukRestockState.Error -> { /* show error */ }
              }
          }
      }



        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        btnPilihProduk.setOnClickListener {
            produkTerpilih.clear()
//            produkTerpilih.addAll(selectedProduk.map { produk ->
//
//            })


            findNavController().navigate(
                R.id.action_pilihProdukRestokFragment_to_tambahRestokFragment
            )
        }
    }
}
