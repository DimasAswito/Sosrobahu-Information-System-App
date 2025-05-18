package com.polije.sosrobahufactoryapp.ui.agen.order.tambahOrder

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentTambahOrderAgenBinding
import com.polije.sosrobahufactoryapp.databinding.LoadingSuccessOverlayBinding
import com.polije.sosrobahufactoryapp.ui.agen.order.component.TambahOrderAgenAdapter
import com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen.SelectedProdukAgen
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

import kotlin.getValue

class TambahOrderAgenFragment : Fragment() {

    private var _binding: FragmentTambahOrderAgenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TambahOrderAgenViewModel by viewModel()
    private lateinit var successOverlayBinding: LoadingSuccessOverlayBinding
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private val args : TambahOrderAgenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahOrderAgenBinding.inflate(layoutInflater, container, false)
        successOverlayBinding = LoadingSuccessOverlayBinding.inflate(inflater)
        (binding.root as ViewGroup).addView(successOverlayBinding.root)
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

        viewModel.initialProdukRestock(args.selectedBarang.data)

        val adapter = TambahOrderAgenAdapter(object : TambahOrderAgenAdapter.OnQuantityChangeListener {
            override fun onQuantityChanged(
                item: SelectedProdukAgen,
                newQty: Int
            ) {
                viewModel.updateQuantity(item.item.idBarangDistributor, newQty)
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
                        successOverlayBinding.loadingLayoutSuccess.visibility = View.VISIBLE

                        Handler(Looper.getMainLooper()).postDelayed({
                            successOverlayBinding.loadingLayoutSuccess.visibility = View.GONE
                            findNavController().navigateUp()
                        }, 2000)                    }
                }
            }
        }

        binding.btnTambahOrder.setOnClickListener {
            viewModel.submitOrder()
        }
        binding.textTransferInfo.text = getString(
            R.string.transfer_info_text,
            args.selectedBarang.namaBank,
            args.selectedBarang.norek.toString(),
            args.selectedBarang.namaLengkap
        )

    }
}