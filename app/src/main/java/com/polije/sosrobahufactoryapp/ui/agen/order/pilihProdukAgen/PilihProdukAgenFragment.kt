package com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.databinding.FragmentPilihProdukAgenBinding
import kotlin.getValue

class PilihProdukAgenFragment : Fragment() {

    private var _binding: FragmentPilihProdukAgenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PilihProdukAgenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPilihProdukAgenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}