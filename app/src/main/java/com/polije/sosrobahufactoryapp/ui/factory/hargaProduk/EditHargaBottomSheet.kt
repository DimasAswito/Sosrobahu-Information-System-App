package com.polije.sosrobahufactoryapp.ui.factory.hargaProduk

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.polije.sosrobahufactoryapp.R

class EditHargaBottomSheet : BottomSheetDialogFragment() {

    private lateinit var etHargaBaru: EditText
    private lateinit var btnSimpan: Button

    interface OnHargaUpdatedListener {
        fun onHargaUpdated(hargaBaru: String)
    }

    private var listener: OnHargaUpdatedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is OnHargaUpdatedListener) {
            listener = parentFragment as OnHargaUpdatedListener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_edit_harga, container, false)

        etHargaBaru = view.findViewById(R.id.etHargaBaru)
        btnSimpan = view.findViewById(R.id.btnSimpanHarga)

        btnSimpan.setOnClickListener {
            val hargaBaru = etHargaBaru.text.toString().trim()
            if (hargaBaru.isNotEmpty()) {
                listener?.onHargaUpdated(hargaBaru)
                dismiss()
            } else {
                etHargaBaru.error = "Harga tidak boleh kosong"
            }
        }

        return view
    }
}
