package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.tambahToko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.databinding.FragmentTambahTokoBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TambahTokoFragment : Fragment() {

    private var _binding: FragmentTambahTokoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TambahTokoViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahTokoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etNamaToko.doAfterTextChanged { text -> viewModel.onNamaTokoChange(text.toString()) }
        binding.etPemilik.doAfterTextChanged { text -> viewModel.onNamaPemilikChange(text.toString()) }
        binding.etTelp.doAfterTextChanged { text -> viewModel.onNoTelpChanged(text.toString()) }
        binding.etLokasi.doAfterTextChanged { text -> viewModel.onLokasiChanged(text.toString()) }


        binding.btnTambahToko.setOnClickListener {
            viewModel.insertToko()
        }


        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.btnTambahToko.isEnabled = state.isValid

                if (state.isSubmitted) {
                    findNavController().navigateUp()
                }
            }
        }
    }
}