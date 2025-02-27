package com.polije.sosrobahufactoryapp.ui.factory.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.ui.factory.FactoryActivity

class DetailLaporanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_laporan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tangkap data yang dikirim (misal lewat safe args atau argument manual)
        val distributor = arguments?.getString("distributor") ?: "Distributor X"
        val tanggal = arguments?.getString("tanggal") ?: "01/01/2025"
        val jumlah = arguments?.getInt("jumlah") ?: 0
        val totalHarga = arguments?.getString("totalHarga") ?: "Rp 0"

        // Bind ke tampilan
        view.findViewById<TextView>(R.id.tvDistributorDetail).text = distributor
        view.findViewById<TextView>(R.id.tvTanggalDetail).text = "Tanggal: $tanggal"
        view.findViewById<TextView>(R.id.tvJumlahDetail).text = "Jumlah: $jumlah"
        view.findViewById<TextView>(R.id.tvTotalHargaDetail).text = "Total: $totalHarga"

        // Bagian "Rincian Tagihan" hanya contoh, Anda bisa hitung manual:
        view.findViewById<TextView>(R.id.tvTotalHargaDetail).text = "Sub Total: $totalHarga"
        // Sisanya, misal PPN, Materai, dsb. Anda bisa set manual atau kalkulasi

        // Tombol Cetak
        view.findViewById<Button>(R.id.CetakLaporanButton).setOnClickListener {
            Toast.makeText(requireContext(), "Cetak nota untuk $distributor", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? FactoryActivity)?.hideBottomNav()
    }

    override fun onPause() {
        super.onPause()
        (activity as? FactoryActivity)?.showBottomNav()
    }
}
