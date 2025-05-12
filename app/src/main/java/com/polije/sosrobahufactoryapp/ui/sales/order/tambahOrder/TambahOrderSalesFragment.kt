package com.polije.sosrobahufactoryapp.ui.sales.order.tambahOrder

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentTambahOrderSalesBinding
import com.polije.sosrobahufactoryapp.ui.sales.order.component.TambahOrderSalesAdapter
import com.polije.sosrobahufactoryapp.ui.sales.order.pilihProdukSales.SelectedProdukSales
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TambahOrderSalesFragment : Fragment() {

    private var _binding: FragmentTambahOrderSalesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TambahOrderSalesViewModel by viewModel()

    private val args: TambahOrderSalesFragmentArgs by navArgs()


    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahOrderSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    viewModel.updateBuktiTransfer(uri)
                }
            }

        binding.cardViewUpload.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

//        val mainNavHost = requireActivity()
//            .supportFragmentManager
//            .findFragmentById(R.id.fragmentContainerView)
//                as NavHostFragment


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()

        }

        viewModel.initialProdukRestock(args.selectedItemAgenSales.data)

        val adapter =
            TambahOrderSalesAdapter(object : TambahOrderSalesAdapter.OnQuantityChangeListener {
                override fun onQuantityChanged(
                    item: SelectedProdukSales,
                    newQty: Int
                ) {
                    viewModel.updateQuantity(item.item.idBarangAgen, newQty)
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

            launch {
                viewModel.tambahOrderDistributorState.collectLatest { state ->
                    if (state.isSubmitted) {
                        findNavController().navigateUp()
                    }
                }
            }
        }

        binding.btnTambahOrder.setOnClickListener {
            viewModel.submitOrder()
        }
//        binding.textTransferInfo.text = getString(
//            R.string.transfer_info_text,
//            args.selectedBarang.namaBank,
//            args.selectedBarang.norek.toString(),
//            args.selectedBarang.namaLengkap
//        )

    }
}