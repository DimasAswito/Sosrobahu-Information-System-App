package com.polije.sosrobahufactoryapp.ui.distributor.pesanan

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polije.sosrobahufactoryapp.R

class PesananDistributorFragment : Fragment() {

    companion object {
        fun newInstance() = PesananDistributorFragment()
    }

    private val viewModel: PesananDistributorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pesanan_agen, container, false)
    }
}