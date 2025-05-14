package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentBottomSheetTambahKunjunganTokoBinding
import java.util.Calendar

class BottomSheetTambahKunjunganTokoFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetTambahKunjunganTokoBinding? = null
    private val binding get() = _binding!!

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.imageViewKunjungan.setImageURI(it)
                binding.imageViewKunjungan.visibility = View.VISIBLE
                binding.uploadText.visibility = View.GONE
                binding.uploadIcon.visibility = View.GONE
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
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

        binding.etTanggal.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    val date = String.format(buildString {append("%02d/%02d/%04d")
    }, day, month + 1, year)
                    binding.etTanggal.setText(date)
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
            dismiss()
        }

        return binding.root
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
}
