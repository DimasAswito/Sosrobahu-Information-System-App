package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.model.RiwayatRestok
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.RiwayatRestokAdapter

class RiwayatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var riwayatAdapter: RiwayatRestokAdapter
    private var riwayatList = mutableListOf<RiwayatRestok>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_riwayat, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewRiwayat)

        // Dummy Data
        riwayatList = mutableListOf(
            RiwayatRestok("Produk A", "10 Februari 2025", 50, R.drawable.logo),
            RiwayatRestok("Produk B", "8 Februari 2025", 30, R.drawable.logo),
            RiwayatRestok("Produk C", "5 Februari 2025", 70, R.drawable.logo),
            RiwayatRestok("Produk D", "3 Februari 2025", 40, R.drawable.logo)
        )

        riwayatAdapter = RiwayatRestokAdapter(riwayatList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = riwayatAdapter

        return view
    }
}
