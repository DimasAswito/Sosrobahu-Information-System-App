package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.detailDaftarToko

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailDaftarTokoSalesBinding

class DetailDaftarTokoSalesFragment : Fragment() {

    private var _binding : FragmentDetailDaftarTokoSalesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailDaftarTokoSalesViewModel by viewModels()

    private val args : DetailDaftarTokoSalesFragmentDirections by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailDaftarTokoSalesBinding.inflate(inflater, container, false  )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}