package com.polije.sosrobahufactoryapp.ui.factory.hargaProduk.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.ui.factory.hargaProduk.component.EditHargaBottomSheet

class DetailHargaPabrikFragment : Fragment(), EditHargaBottomSheet.OnHargaUpdatedListener {

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

        val nama = arguments?.getString("namaProduk")
        val harga = arguments?.getString("hargaProduk")
        val gambar = arguments?.getInt("gambarProduk")

        txtNamaProduk.text = nama
        txtHargaProduk.text = harga
        gambar?.let { imgProduk.setImageResource(it) }

        btnEditHarga.setOnClickListener {
            val bottomSheet = EditHargaBottomSheet()
            bottomSheet.show(childFragmentManager, "EditHargaBottomSheet")
        }

        return view
    }

    override fun onHargaUpdated(hargaBaru: String) {
        txtHargaProduk.text = hargaBaru
    }
}
