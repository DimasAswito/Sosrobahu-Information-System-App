package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.detailRestok

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

class DetailRestokFragment : Fragment() {

    private lateinit var tvNamaProduk: TextView
    private lateinit var tvTanggalRestok: TextView
    private lateinit var tvJumlahProduk: TextView
    private lateinit var btnCetak: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_restok, container, false)

        tvNamaProduk = view.findViewById(R.id.tvNamaProduk)
        tvTanggalRestok = view.findViewById(R.id.tvTanggalRestok)
        tvJumlahProduk = view.findViewById(R.id.tvJumlahProduk)
        btnCetak = view.findViewById(R.id.btnCetak)

        // Ambil data yang dikirim dari RiwayatFragment
        val bundle = arguments
        if (bundle != null) {
            tvNamaProduk.text = bundle.getString("nama_produk", "Nama Produk")
            tvTanggalRestok.text = "Tanggal: " + bundle.getString("tanggal", "Tidak diketahui")
            tvJumlahProduk.text = "Jumlah: " + bundle.getInt("jumlah", 0).toString()
        }

        // Aksi tombol cetak (bisa dihubungkan dengan fungsi cetak PDF)
        btnCetak.setOnClickListener {
            Toast.makeText(requireContext(), "Mencetak riwayat...", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
