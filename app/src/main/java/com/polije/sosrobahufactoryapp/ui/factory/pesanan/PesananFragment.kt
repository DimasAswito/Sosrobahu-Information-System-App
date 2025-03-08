package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.model.Pesanan
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.component.PesananAdapter

class PesananFragment : Fragment() {

    private lateinit var pesananAdapter: PesananAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var btnFilter: ImageView
    private var pesananList = mutableListOf<Pesanan>()
    private var filteredList = mutableListOf<Pesanan>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pesanan, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewPesanan)
        searchView = view.findViewById(R.id.searchPesananFactory)
//        btnFilter = view.findViewById(R.id.btnFilter)

        // Dummy Data
        pesananList = mutableListOf(
            Pesanan("Distributor A", "2024-02-11", 500000, "Diproses"),
            Pesanan("Distributor C", "2024-02-09", 300000, "Diproses"),
            Pesanan("Distributor B", "2024-02-10", 750000, "Selesai"),
            Pesanan("Distributor D", "2024-02-08", 400000, "Ditolak")

        )

        filteredList.addAll(pesananList) // Default tampilkan semua data

        pesananAdapter = PesananAdapter(filteredList, findNavController())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = pesananAdapter

//        btnFilter.setOnClickListener {
//            showFilterDialog()
//        }

        return view
    }


    private fun showFilterDialog() {
        val options = arrayOf("Urut berdasarkan Tanggal", "Urut berdasarkan Nama", "Tampilkan Semua")

        AlertDialog.Builder(requireContext())
            .setTitle("Filter Pesanan")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> sortPesananByTanggal()
                    1 -> sortPesananByNama()
                    2 -> resetPesanan()
                }
            }
            .show()
    }

    private fun sortPesananByTanggal() {
        filteredList.sortBy { it.tanggal }
        pesananAdapter.notifyDataSetChanged()
    }

    private fun sortPesananByNama() {
        filteredList.sortBy { it.distributor }
        pesananAdapter.notifyDataSetChanged()
    }

    private fun resetPesanan() {
        filteredList.clear()
        filteredList.addAll(pesananList)
        pesananAdapter.notifyDataSetChanged()
    }
}
