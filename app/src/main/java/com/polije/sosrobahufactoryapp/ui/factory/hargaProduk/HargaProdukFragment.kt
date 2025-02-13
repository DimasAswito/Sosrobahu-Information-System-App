package com.polije.sosrobahufactoryapp.ui.factory.hargaProduk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.model.Produk

class HargaProdukFragment : Fragment() {

    private lateinit var produkAdapter: ProdukAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchViewProduk: SearchView
    private lateinit var fabTambahProduk: FloatingActionButton
    private var produkList = mutableListOf<Produk>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_harga_produk, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewHarga)
        searchViewProduk = view.findViewById(R.id.searchViewProduk)
        fabTambahProduk = view.findViewById(R.id.fabTambahProduk)

        // Dummy data produk
        produkList = mutableListOf(
            Produk("Produk A", "Rp 10.000", R.drawable.logo),
            Produk("Produk B", "Rp 20.000", R.drawable.logo),
            Produk("Produk C", "Rp 15.000", R.drawable.logo),
            Produk("Produk D", "Rp 30.000", R.drawable.logo)
        )

        produkAdapter = ProdukAdapter(produkList)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = produkAdapter

        fabTambahProduk.setOnClickListener {
            Toast.makeText(requireContext(), "Tambah Produk", Toast.LENGTH_SHORT).show()
        }

        // Tambahkan listener untuk SearchView
        searchViewProduk.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                produkAdapter.filter(newText ?: "")
                return true
            }
        })

        return view
    }
}
