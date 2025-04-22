package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import Pesanan
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.PesananMasukItem
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.component.PesananPabrikAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PesananPabrikFragment : Fragment() {

    private lateinit var pesananPabrikAdapter: PesananPabrikAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var btnFilter: ImageView
    private var pesananList = mutableListOf<Pesanan>()
    private var filteredList = mutableListOf<Pesanan>()

    private val pesananPabrikViewModel : PesananPabrikViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pesanan, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewPesanan)
        searchView = view.findViewById(R.id.searchPesananFactory)
//        btnFilter = view.findViewById(R.id.btnFilter)

        // Dummy Data
        pesananList = mutableListOf(
//            Pesanan("Distributor A", "2024-02-11", 500000,"", 0),
//            Pesanan("Distributor C", "2024-02-09", 300000, "Diproses"),
//            Pesanan("Distributor B", "2024-02-10", 750000, "Selesai"),
//            Pesanan("Distributor D", "2024-02-08", 400000, "Ditolak")

        )

        filteredList.addAll(pesananList) // Default tampilkan semua data

        pesananPabrikAdapter = PesananPabrikAdapter(object : PesananPabrikAdapter.OnItemClickListener {
            override fun onItemClick(pesanan: PesananMasukItem) {
                val action = PesananPabrikFragmentDirections.actionNavigationPesananToDetailPesananFragment(pesanan)
                findNavController().navigate(action)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = pesananPabrikAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            pesananPabrikViewModel.getPesananMasuk.collectLatest { pesananMasuk ->
                pesananPabrikAdapter.submitData(pesananMasuk)
            }
        }

//        btnFilter.setOnClickListener {
//            showFilterDialog()
//        }

        return view
    }


    private fun showFilterDialog() {
        val options =
            arrayOf("Urut berdasarkan Tanggal", "Urut berdasarkan Nama", "Tampilkan Semua")

        AlertDialog.Builder(requireContext()).setTitle("Filter Pesanan")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> sortPesananByTanggal()
                    1 -> sortPesananByNama()
                    2 -> resetPesanan()
                }
            }.show()
    }

    private fun sortPesananByTanggal() {
        filteredList.sortBy { it.tanggal }
        pesananPabrikAdapter.notifyDataSetChanged()
    }

    private fun sortPesananByNama() {
        filteredList.sortBy { it.idUserDistributor }
        pesananPabrikAdapter.notifyDataSetChanged()
    }

    private fun resetPesanan() {
        filteredList.clear()
        filteredList.addAll(pesananList)
        pesananPabrikAdapter.notifyDataSetChanged()
    }
}
