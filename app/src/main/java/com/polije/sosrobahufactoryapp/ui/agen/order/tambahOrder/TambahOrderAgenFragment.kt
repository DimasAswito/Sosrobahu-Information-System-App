package com.polije.sosrobahufactoryapp.ui.agen.order.tambahOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.databinding.FragmentTambahOrderAgenBinding

import kotlin.getValue

class TambahOrderAgenFragment : Fragment() {

    private var _binding: FragmentTambahOrderAgenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TambahOrderAgenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahOrderAgenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}