package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.polije.sosrobahufactoryapp.databinding.FragmentBottomSheetEditTokoBinding

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

        // ambil argumen awal
        val nama   = arguments?.getString("namaToko") ?: ""
        val telp   = arguments?.getString("noTelp")    ?: ""
        val alamat = arguments?.getString("alamat")    ?: ""

        binding.etNamaToko.setText(nama)
        binding.etNoTelepon.setText(telp)
        binding.etAlamat.setText(alamat)

        binding.btnUpdateToko.setOnClickListener {
            // prepare hasil
            val result = bundleOf(
                "namaToko" to binding.etNamaToko.text.toString(),
                "noTelp"   to binding.etNoTelepon.text.toString(),
                "alamat"   to binding.etAlamat.text.toString()
            )
            // kirim ke parent
            parentFragmentManager.setFragmentResult("EDIT_TOKO", result)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(nama: String, telp: String, alamat: String): BottomSheetEditTokoFragment {
            val fragment = BottomSheetEditTokoFragment()
            fragment.arguments = bundleOf(
                "namaToko" to nama,
                "noTelp"   to telp,
                "alamat"   to alamat
            )
            return fragment
        }
    }
}
