package com.polije.sosrobahufactoryapp.ui.distributor.order.tambahOrder

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentTambahOrderDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.TambahOrderDistributorAdapter
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.TambahOrderDistributorAdapter.OnQuantityChangeListener
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

class TambahOrderDistributorFragment : Fragment() {

    private var _binding: FragmentTambahOrderDistributorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TambahOrderDistributorViewModel by viewModels()
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private val args: TambahOrderDistributorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahOrderDistributorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    viewModel.updateBuktiTransfer(uri)
                }
            }

        binding.cardViewUpload.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.initialProdukRestock(args.listItemTerpilih.data)

        val adapter = TambahOrderDistributorAdapter(object : OnQuantityChangeListener {
            override fun onQuantityChanged(
                item: SelectedProdukDistributor,
                newQty: Int
            ) {
                viewModel.updateQuantity(item.item.idMasterBarang ?: 0, newQty)
            }
        })
        binding.recyclerViewTambahOrder.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTambahOrder.adapter = adapter

        lifecycleScope.launch {
            launch {
                viewModel.produkRestock.collectLatest {
                    adapter.submitList(it)
                }
            }

            launch {
                viewModel.buktiTransfer.collectLatest { photo ->
                    if (photo != Uri.EMPTY) {
                        binding.imageViewPreviewNota.setImageURI(photo)
                        binding.imageViewPreviewNota.visibility = View.VISIBLE
                        binding.imageViewBuktiTransfer.visibility = View.GONE
                    }
                }
            }

            launch {
                viewModel.isValid.collectLatest {
                    binding.btnTambahOrder.isEnabled = it
                }
            }

            launch {
                viewModel.totalHarga.collectLatest {
                    binding.tvTotalHargaNominal.text = it.toRupiah()
                }
            }
        }

        binding.textTransferInfo.text = getString(
            R.string.transfer_info_text,
            args.listItemTerpilih.namaBank,
            args.listItemTerpilih.norek.toString(),
            args.listItemTerpilih.namaLengkap
        )
    }

    fun getFileFromUri(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context?.contentResolver?.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val filePath = cursor?.getString(columnIndex!!)
        cursor?.close()
        return filePath?.let { File(it) }
    }

}