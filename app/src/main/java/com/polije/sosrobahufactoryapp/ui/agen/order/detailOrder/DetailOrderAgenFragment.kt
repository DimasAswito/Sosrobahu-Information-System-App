package com.polije.sosrobahufactoryapp.ui.agen.order.detailOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailOrderAgenBinding
import kotlin.getValue

class DetailOrderAgenFragment : Fragment() {


    private var _binding: FragmentDetailOrderAgenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailOrderAgenViewModel by viewModels()

//    private val args: DetailOrderAgenFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailOrderAgenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnCetak.setOnClickListener {
            Toast.makeText(requireContext(), "Mencetak riwayat...", Toast.LENGTH_SHORT).show()
        }
    }
}