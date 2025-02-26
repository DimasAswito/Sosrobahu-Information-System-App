package com.polije.sosrobahufactoryapp.ui.factory.hargaProduk

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.polije.sosrobahufactoryapp.R

class DetailHargaFragment : Fragment() {

    private lateinit var imgProduk: ImageView
    private lateinit var txtNamaProduk: TextView
    private lateinit var txtHargaProduk: TextView
    private lateinit var btnEditHarga: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_harga, container, false)

        imgProduk = view.findViewById(R.id.imgDetailProduk)
        txtNamaProduk = view.findViewById(R.id.txtDetailNamaProduk)
        txtHargaProduk = view.findViewById(R.id.txtDetailHargaProduk)
        btnEditHarga = view.findViewById(R.id.btnEditHarga)

        // Ambil data dari Bundle
        val nama = arguments?.getString("namaProduk")
        val harga = arguments?.getString("hargaProduk")
        val gambar = arguments?.getInt("gambarProduk")

        // Tampilkan data di UI
        txtNamaProduk.text = nama
        txtHargaProduk.text = harga
        gambar?.let { imgProduk.setImageResource(it) }

        btnEditHarga.setOnClickListener {
            Toast.makeText(requireContext(), "Edit Harga", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
