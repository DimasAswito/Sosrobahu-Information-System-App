package com.polije.sosrobahufactoryapp.ui.distributor.order.component.editHarga

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
import com.polije.sosrobahufactoryapp.databinding.BottomSheetEditHargaBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Integer.parseInt

class BottomSheetEditHargaDistributorFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetEditHargaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BottomSheetEditHargaDistributorViewModel by viewModel()

    private val args: BottomSheetEditHargaDistributorFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =
            BottomSheetEditHargaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etHargaBaru.doAfterTextChanged { viewModel.updateHargaField(parseInt(it.toString())) }

        binding.btnSimpanHarga.setOnClickListener {
            viewModel.updateHarga(args.barang.id)
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
                    binding.btnSimpanHarga.isEnabled = it
                }
            }

        }
    }

}