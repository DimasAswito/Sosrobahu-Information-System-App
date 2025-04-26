package com.polije.sosrobahufactoryapp.ui.factory.home.laporanBulanan

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.pabrik.LaporanBulanan
import com.polije.sosrobahufactoryapp.ui.factory.home.component.LaporanBulananPabrikAdapter

class LaporanBulananFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var laporanAdapter: LaporanBulananPabrikAdapter
    // laporanList akan digunakan sebagai data yang ditampilkan saat ini
    private var laporanList = mutableListOf<LaporanBulanan>()
    // allLaporanList sebagai data master (seluruh data)
    private var allLaporanList = mutableListOf<LaporanBulanan>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_laporan_bulanan, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewLaporan)
        val searchView = view.findViewById<SearchView>(R.id.searchViewlaporanbulanan)
        val filterButton = view.findViewById<ImageView>(R.id.filterButtonLaporanBulanan)

        // Tambahkan data dummy untuk 12 bulan (2 item per bulan)
        for (month in 1..12) {
            val monthStr = if (month < 10) "0$month" else "$month"
            allLaporanList.add(LaporanBulanan("Distributor A", "01/$monthStr/2025", 10, "Rp 5.000.000"))
            allLaporanList.add(LaporanBulanan("Distributor B", "05/$monthStr/2025", 8, "Rp 4.000.000"))
        }
        // Inisialisasi laporanList dengan semua data
        laporanList.addAll(allLaporanList)

        laporanAdapter = LaporanBulananPabrikAdapter(laporanList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = laporanAdapter

        // Setup SearchView: filter berdasarkan nama distributor
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterData(query, null)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterData(newText, null)
                return true
            }
        })

        // Setup Filter Button: menampilkan dialog untuk pilih bulan
        filterButton.setOnClickListener {
            showMonthFilterDialog()
        }

        return view
    }

    // Fungsi untuk memfilter data berdasarkan query (nama distributor) dan/atau bulan
    private fun filterData(query: String?, month: String?) {
        val filtered = allLaporanList.filter { laporan ->
            val matchesQuery = query.isNullOrEmpty() || laporan.distributor.contains(query, ignoreCase = true)
            // Asumsikan format tanggal "dd/MM/yyyy", ambil bulan dengan substring(3,5)
            val laporanMonth = laporan.tanggal.substring(3, 5)
            val matchesMonth = month.isNullOrEmpty() || laporanMonth == month
            matchesQuery && matchesMonth
        }
        laporanList.clear()
        laporanList.addAll(filtered)
        laporanAdapter.notifyDataSetChanged()
    }

    // Menampilkan dialog pilihan bulan
    private fun showMonthFilterDialog() {
        // Pilihan: "All" diikuti oleh bulan "01" hingga "12"
        val monthsArray = arrayOf("Semua", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
        AlertDialog.Builder(requireContext())
            .setTitle("Pilih Bulan")
            .setItems(monthsArray) { _, which ->
                val selectedMonth = if (which == 0) null else monthsArray[which]
                // Ambil query pencarian saat ini (jika ada)
                val currentQuery = view?.findViewById<SearchView>(R.id.searchViewlaporanbulanan)?.query?.toString()
                filterData(currentQuery, selectedMonth)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

//    override fun onResume() {
//        super.onResume()
//        (activity as? FactoryActivity)?.hideBottomNav()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        (activity as? FactoryActivity)?.showBottomNav()
//    }
}

