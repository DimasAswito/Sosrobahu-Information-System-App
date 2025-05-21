package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.tambahKunjungan

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.polije.sosrobahufactoryapp.databinding.FragmentBottomSheetTambahKunjunganTokoBinding
import com.polije.sosrobahufactoryapp.utils.getImageUri
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class BottomSheetTambahKunjunganTokoFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetTambahKunjunganTokoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BottomSheetTambahKunjunganViewModel by viewModel()

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.updateGambar(uri)
                binding.imageViewKunjungan.setImageURI(it)
                binding.imageViewKunjungan.visibility = View.VISIBLE
                binding.uploadText.visibility = View.GONE
                binding.uploadIcon.visibility = View.GONE
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                viewModel.updateGambar(getImageUri(requireContext(), bitmap) ?: Uri.EMPTY)
                binding.imageViewKunjungan.setImageBitmap(it)
                binding.imageViewKunjungan.visibility = View.VISIBLE
                binding.uploadText.visibility = View.GONE
                binding.uploadIcon.visibility = View.GONE
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetTambahKunjunganTokoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idToko = arguments?.getInt("idToko", 0) ?: 0

        binding.etTanggal.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    val date = String.format(buildString {
                        append("%02d/%02d/%04d")
                    }, day, month + 1, year)
                    binding.etTanggal.setText(date)
                    viewModel.updateTanggal(date)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.cardViewUpload.setOnClickListener {
            showImagePickerDialog()
        }

        binding.btnTambahKunjungan.setOnClickListener {
            viewModel.tambahKunjunganToko(idToko)
        }

        binding.etStok.doAfterTextChanged { viewModel.updateSisaProduk(Integer.parseInt(it.toString())) }

        lifecycleScope.launch {
            launch {
                viewModel.isValid.collectLatest {
                    binding.btnTambahKunjungan.isEnabled = it
                }
            }

            launch {
                viewModel.state.collectLatest { state ->
                    if (state.isSubmitted) {
                        dismiss()
                    }
                }
            }
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Pilih dari Galeri", "Ambil dari Kamera")
        AlertDialog.Builder(requireContext())
            .setTitle("Pilih Gambar")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> imagePickerLauncher.launch("image/*")
                    1 -> cameraLauncher.launch(null)
                }
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(idToko: Int): BottomSheetTambahKunjunganTokoFragment {
            val fragment = BottomSheetTambahKunjunganTokoFragment()
            fragment.arguments = bundleOf(
                "idToko" to idToko
            )
            return fragment
        }
    }
}
