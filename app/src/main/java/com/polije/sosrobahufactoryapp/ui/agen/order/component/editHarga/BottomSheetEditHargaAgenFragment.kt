package com.polije.sosrobahufactoryapp.ui.agen.order.component.editHarga

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

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

        val localeID = Locale("id", "ID")
        val dfs = DecimalFormatSymbols(localeID).apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }
        val formatter = DecimalFormat("#,###", dfs)

        // 2) Keep track of the last formatted text to avoid recursion
        var lastFormatted = ""

        val hargaWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s?.toString() ?: return
                // if no change (or already formatted), bail out
                if (input == lastFormatted) return

                // strip non-digits
                val digitsOnly = input.replace("[^0-9]".toRegex(), "")
                if (digitsOnly.isEmpty()) {
                    lastFormatted = ""
                    return
                }

                // parse & re-format
                val parsed = digitsOnly.toLong()
                val formatted = formatter.format(parsed)

                // remember & apply without re-triggering watcher
                lastFormatted = formatted
                binding.etHargaProduk.removeTextChangedListener(this)
                binding.etHargaProduk.setText(formatted)
                binding.etHargaProduk.setSelection(formatted.length)
                binding.etHargaProduk.addTextChangedListener(this)


                // update your VM with the raw Int
                viewModel.updateHargaField(parsed.toInt())
            }
        }

        binding.etHargaProduk.addTextChangedListener(hargaWatcher)

        binding.etHargaProduk.setText(args.rokokitem.harga.toString())

        binding.btnEditHarga.setOnClickListener {
            viewModel.updateHarga(args.rokokitem.id)
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