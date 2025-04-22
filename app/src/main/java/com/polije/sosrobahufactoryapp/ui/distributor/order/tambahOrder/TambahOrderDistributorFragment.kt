package com.polije.sosrobahufactoryapp.ui.distributor.order.tambahOrder

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polije.sosrobahufactoryapp.R

class TambahOrderDistributorFragment : Fragment() {

    companion object {
        fun newInstance() = TambahOrderDistributorFragment()
    }

    private val viewModel: TambahOrderDistributorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tambah_order_distributor, container, false)
    }
}