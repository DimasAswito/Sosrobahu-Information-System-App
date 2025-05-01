package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.detailDaftarToko

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polije.sosrobahufactoryapp.R

class DetailDaftarTokoSalesFragment : Fragment() {

    companion object {
        fun newInstance() = DetailDaftarTokoSalesFragment()
    }

    private val viewModel: DetailDaftarTokoSalesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail_daftar_toko_sales, container, false)
    }
}