package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentBottomSheetEditTokoBinding
import com.polije.sosrobahufactoryapp.databinding.FragmentBottomSheetTambahKunjunganTokoBinding

class BottomSheetEditTokoFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetEditTokoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetEditTokoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nama = arguments?.getString("namaToko") ?: ""
        val telp = arguments?.getString("noTelp") ?: ""
        val alamat = arguments?.getString("alamat") ?: ""

        binding.etNamaToko.setText(nama)
        binding.etNoTelepon.setText(telp)
        binding.etAlamat.setText(alamat)
    }

    companion object {
        fun newInstance(nama: String, telp: String, alamat: String): BottomSheetEditTokoFragment {
            val fragment = BottomSheetEditTokoFragment()
            val args = Bundle()
            args.putString("namaToko", nama)
            args.putString("noTelp", telp)
            args.putString("alamat", alamat)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}