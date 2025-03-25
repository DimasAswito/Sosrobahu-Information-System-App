package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.detailRestok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailRestokBinding

class DetailRestokFragment : Fragment() {

    private lateinit var tvNamaProduk: TextView
    private lateinit var tvTanggalRestok: TextView
    private lateinit var tvJumlahProduk: TextView
    private lateinit var btnCetak: Button

    private var _binding: FragmentDetailRestokBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailRestokBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvNamaProduk = binding.tvNamaProduk
        tvTanggalRestok = binding.tvTanggalRestok
        tvJumlahProduk =binding.tvJumlahProduk
        btnCetak = binding.btnCetak

        // Ambil data yang dikirim dari RiwayatFragment


        // Aksi tombol cetak (bisa dihubungkan dengan fungsi cetak PDF)
        btnCetak.setOnClickListener {
            Toast.makeText(requireContext(), "Mencetak riwayat...", Toast.LENGTH_SHORT).show()
        }
    }
}
