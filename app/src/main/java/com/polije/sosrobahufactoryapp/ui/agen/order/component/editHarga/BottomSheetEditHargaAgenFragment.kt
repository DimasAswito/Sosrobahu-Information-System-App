package com.polije.sosrobahufactoryapp.ui.agen.order.component.editHarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.polije.sosrobahufactoryapp.databinding.FragmentBottomSheetEditHargaAgenBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Integer.parseInt

class BottomSheetEditHargaAgenFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetEditHargaAgenBinding? = null
    private val binding get() = _binding!!


    private val viewModel: BottomSheetEditHargaAgenViewModel by viewModel()
    private val args: BottomSheetEditHargaAgenFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetEditHargaAgenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etHargaProduk.doAfterTextChanged { viewModel.updateHargaField(parseInt(it.toString())) }

        binding.btnEditHarga.setOnClickListener {
            viewModel.updateHarga(args.id)
        }

        lifecycleScope.launch {
            launch {
                viewModel.isSubmitted.collectLatest {
                    if (it) {
                        findNavController().previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("refresh", true)

                        findNavController().popBackStack()
                    }
                }
            }

            launch {
                viewModel.errorMessage.collectLatest {
                    it?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            launch {
                viewModel.isValid.collectLatest {
                    binding.btnEditHarga.isEnabled = it
                }
            }

        }
    }
}