package com.polije.sosrobahufactoryapp.ui.factory.hargaProduk.tambahProduk

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.polije.sosrobahufactoryapp.R

class TambahProdukFragment : Fragment() {

    private lateinit var imgPreview: ImageView
    private lateinit var etNamaProduk: EditText
    private lateinit var etHargaProduk: EditText
    private lateinit var btnTambahProduk: Button
    private lateinit var layoutTambahGambar: LinearLayout
    private var selectedImageUri: Uri? = null

    // Activity Result API untuk memilih gambar
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            imgPreview.visibility = View.VISIBLE
            layoutTambahGambar.visibility = View.GONE
            imgPreview.setImageURI(selectedImageUri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tambah_produk, container, false)

        val frameTambahGambar = view.findViewById<CardView>(R.id.frameTambahGambar)
        layoutTambahGambar = view.findViewById(R.id.layoutTambahGambar)

        imgPreview = view.findViewById(R.id.imgPreview)
        etNamaProduk = view.findViewById(R.id.etNamaProduk)
        etHargaProduk = view.findViewById(R.id.etHargaProduk)
        btnTambahProduk = view.findViewById(R.id.btnTambahProduk)

        frameTambahGambar.setOnClickListener { openMediaPicker() }

        btnTambahProduk.setOnClickListener {
            Toast.makeText(requireContext(), "Produk Ditambahkan!", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }

    private fun openMediaPicker() {
        pickImageLauncher.launch("image/*") // Buka media picker
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

