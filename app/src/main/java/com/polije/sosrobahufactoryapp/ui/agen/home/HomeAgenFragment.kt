package com.polije.sosrobahufactoryapp.ui.agen.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeAgenBinding
import com.polije.sosrobahufactoryapp.databinding.LoadingOverlayBinding
import com.polije.sosrobahufactoryapp.ui.agen.dashboard.DashboardAgenFragmentDirections
import com.polije.sosrobahufactoryapp.ui.agen.home.component.ItemHomeAgenAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeAgenFragment : Fragment() {

    private var _binding: FragmentHomeAgenBinding? = null
    private val binding get() = _binding!!

    private lateinit var loadingBinding: LoadingOverlayBinding

    private val homeAgenViewModel: HomeAgenViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeAgenBinding.inflate(layoutInflater, container, false)
        loadingBinding = LoadingOverlayBinding.inflate(inflater)
        (binding.root as ViewGroup).addView(loadingBinding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemHomeAgenAdapter()
        binding.recyclerViewDasboardAgen.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewDasboardAgen.adapter = adapter

        binding.logoutAgenButton.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    homeAgenViewModel.logout()
                    dialog.dismiss()
                }.setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

//        binding.tvlihatProdukTerlaris.setOnClickListener {
//            val action =
//                DashboardAgenFragmentDirections.actionDashboardAgenFragmentToStockAgenRankFragment(
//                listStockProdukAgen
//                )
//            requireActivity().findNavController(R.id.fragmentContainerView).navigate(action)
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeAgenViewModel.isLogged.collectLatest {
                    if (!it) {
                        Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT)
                            .show()
                        val action =
                            DashboardAgenFragmentDirections.actionDashboardAgenFragmentToAgenLoginFragment()
                        activity?.findNavController(R.id.fragmentContainerView)?.navigate(action)
                    }
                }
            }
        }

        lifecycleScope.launch {
            homeAgenViewModel.state.collectLatest { state ->
                when (state) {
                    is HomeAgenState.Failure -> {
                        Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG)
                            .show()
                    }

                    HomeAgenState.Initial -> {
                        loadingBinding.loadingLayout.visibility = View.GONE
                    }


                    HomeAgenState.Loading -> {
                        loadingBinding.loadingLayout.visibility = View.VISIBLE
                    }

                    is HomeAgenState.Success -> {
                        loadingBinding.loadingLayout.visibility = View.GONE
                        val namaAgen =
                            state.dashboardResponse.namaAgen.split(" ").firstOrNull() ?: ""
                        binding.headerTextAgen.text = "Selamat datang $namaAgen,"

                        binding.jumlahSales.text = state.dashboardResponse.totalSales.toString()
                        binding.omsetBulanAgen.text =
                            state.dashboardResponse.totalPendapatan?.toRupiah()
                        binding.totalStokAgen.text =
                            state.dashboardResponse.totalStokKeseluruhan.toString()
                        binding.topProductNameAgen.text = state.dashboardResponse.topProduct

                        val topName = state.dashboardResponse.topProduct
                        binding.topProductNameAgen.text = topName

                        val topProduct = state.dashboardResponse.stokBarang.find {
                            it.namaRokok == topName
                        }

                        val imageName = topProduct?.gambar
                        val imageUrl = BuildConfig.PICTURE_BASE_URL + "produk/" + imageName

                        val circularProgressDrawable =
                            CircularProgressDrawable(requireContext()).apply {
                                strokeWidth = 5f
                                centerRadius = 30f
                                start()
                            }

                        Glide.with(requireContext()).load(imageUrl)
                            .placeholder(circularProgressDrawable).error(R.drawable.foto_error)
                            .into(binding.topProductImageAgen)
                        adapter.submitList(state.dashboardResponse.stokBarang)
                    }

                }
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}