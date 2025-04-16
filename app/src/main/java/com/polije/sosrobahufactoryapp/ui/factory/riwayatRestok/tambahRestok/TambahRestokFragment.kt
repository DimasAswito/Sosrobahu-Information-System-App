package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok

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
import com.polije.sosrobahufactoryapp.data.model.ProdukRestok
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.TambahRestokAdapter

class TambahRestokFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tambahRestokAdapter: TambahRestokAdapter
    private var produkList = mutableListOf<ProdukRestok>() // List produk yang dipilih

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tambah_restok, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewTambahRestok)
        val btnTambahRestok: Button = view.findViewById(R.id.btnTambahRestok)

//        // Mendapatkan produk yang dipilih dari arguments
//        arguments?.getParcelableArrayList<ProdukRestok>("produk_terpilih")?.let {
//            produkList.addAll(it)
//        }

        // Setup RecyclerView
        tambahRestokAdapter = TambahRestokAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = tambahRestokAdapter

        // Tombol Tambah Restok kembali ke RiwayatFragment
        btnTambahRestok.setOnClickListener {
            findNavController().navigate(R.id.action_tambahRestokFragment_to_navigation_riwayat)
        }

        return view
    }
}

