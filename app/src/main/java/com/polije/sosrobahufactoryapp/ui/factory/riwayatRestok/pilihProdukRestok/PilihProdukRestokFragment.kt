package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.model.Produk
import com.polije.sosrobahufactoryapp.model.ProdukRestok
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.PilihProdukAdapter

class PilihProdukRestokFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PilihProdukAdapter
    private var produkTerpilih = mutableListOf<ProdukRestok>()
    private var produkList = mutableListOf<Produk>()
    private val selectedProduk = mutableListOf<Produk>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pilih_produk_restok, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewPilihProduk)
        val btnPilihProduk = view.findViewById<Button>(R.id.btnPilihProduk)

        // Dummy Produk
        produkList = mutableListOf(
            Produk("Produk A", "Rp 10.000", R.drawable.logo),
            Produk("Produk B", "Rp 20.000", R.drawable.logo),
            Produk("Produk C", "Rp 15.000", R.drawable.logo),
            Produk("Produk D", "Rp 30.000", R.drawable.logo)
        )

        adapter = PilihProdukAdapter(produkList) { produk, isChecked ->
            if (isChecked) {
                if (!selectedProduk.contains(produk)) selectedProduk.add(produk)
            } else {
                selectedProduk.remove(produk)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        btnPilihProduk.setOnClickListener {
            produkTerpilih.clear()
            produkTerpilih.addAll(selectedProduk.map { produk ->
                ProdukRestok(namaProduk = produk.nama, gambar = produk.gambar)
            })

            val bundle = Bundle()
            bundle.putParcelableArrayList("produk_terpilih", ArrayList(produkTerpilih))

            findNavController().navigate(
                R.id.action_pilihProdukRestokFragment_to_tambahRestokFragment, bundle
            )
        }


        return view
    }
}
