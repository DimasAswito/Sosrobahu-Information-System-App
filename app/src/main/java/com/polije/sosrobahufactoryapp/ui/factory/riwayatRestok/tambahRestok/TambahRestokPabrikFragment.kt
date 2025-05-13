package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.tambahRestok

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.polije.sosrobahufactoryapp.databinding.FragmentTambahRestokBinding
import com.polije.sosrobahufactoryapp.databinding.LoadingSuccessOverlayBinding
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.TambahRestokPabrikAdapter
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.TambahRestokPabrikAdapter.OnQuantityChangeListener
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.SelectedProdukRestokPabrik
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TambahRestokPabrikFragment : Fragment() {

    private lateinit var tambahRestokPabrikAdapter: TambahRestokPabrikAdapter
    private var _binding: FragmentTambahRestokBinding? = null
    private val binding get() = _binding!!

    private lateinit var successOverlayBinding: LoadingSuccessOverlayBinding


    private val tambahRestokPabrikViewModel: TambahRestokPabrikViewModel by viewModel()

    private val args: TambahRestokPabrikFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTambahRestokBinding.inflate(layoutInflater, container, false)
        successOverlayBinding = LoadingSuccessOverlayBinding.inflate(inflater)
        (binding.root as ViewGroup).addView(successOverlayBinding.root)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tambahRestokPabrikViewModel.initialProdukRestock(args.listProdukTerpilih.data)
        tambahRestokPabrikAdapter = TambahRestokPabrikAdapter(object : OnQuantityChangeListener {
            override fun onQuantityChanged(
                produk: SelectedProdukRestokPabrik,
                newQty: Int
            ) {
                tambahRestokPabrikViewModel.updateQuantity(produk.item.idMasterBarang, newQty)
            }

        })
        binding.recyclerViewTambahRestok.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTambahRestok.adapter = tambahRestokPabrikAdapter

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        lifecycleScope.launch {

            launch {
                tambahRestokPabrikViewModel.state.collectLatest { state ->
                    when (state) {
                        TambahRestokPabrikState.Failure -> {
                            binding.btnTambahRestok.isEnabled = false

                        }

                        TambahRestokPabrikState.Initial -> {}
                        TambahRestokPabrikState.Loading -> {
                            binding.btnTambahRestok.isEnabled = true
                        }

                        TambahRestokPabrikState.Success -> {
                            binding.btnTambahRestok.isEnabled = false
                            successOverlayBinding.loadingLayoutSuccess.visibility = View.VISIBLE

                            Handler(Looper.getMainLooper()).postDelayed({
                                successOverlayBinding.loadingLayoutSuccess.visibility = View.GONE
                                findNavController().navigateUp()
                            }, 2000)
                        }
                    }
                }
            }

            launch {
                tambahRestokPabrikViewModel.isValid.collectLatest { isValid ->
                    binding.btnTambahRestok.isEnabled = isValid
                }
            }

            launch {
                tambahRestokPabrikViewModel.produkRestock.collectLatest { data ->
                    tambahRestokPabrikAdapter.submitList(data)
                }
            }

            binding.btnTambahRestok.setOnClickListener {
                tambahRestokPabrikViewModel.insertRestock()
            }
        }
    }
}

