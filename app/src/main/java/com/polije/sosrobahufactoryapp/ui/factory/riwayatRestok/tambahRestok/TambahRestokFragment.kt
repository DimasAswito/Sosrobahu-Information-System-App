package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polije.sosrobahufactoryapp.R

class TambahRestokFragment : Fragment() {

    companion object {
        fun newInstance() = TambahRestokFragment()
    }

    private val viewModel: TambahRestokViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tambah_restok, container, false)
    }
}