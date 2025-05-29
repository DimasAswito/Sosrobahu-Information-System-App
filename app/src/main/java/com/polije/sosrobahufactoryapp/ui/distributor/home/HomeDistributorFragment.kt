package com.polije.sosrobahufactoryapp.ui.distributor.home

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
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeDistributorBinding
import com.polije.sosrobahufactoryapp.databinding.LoadingOverlayBinding
import com.polije.sosrobahufactoryapp.ui.distributor.dashboard.DashboardDistributorFragmentDirections
import com.polije.sosrobahufactoryapp.ui.distributor.home.component.ItemHomeDistributorAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class HomeDistributorFragment : Fragment() {

    private var _binding: FragmentHomeDistributorBinding? = null
    private val binding get() = _binding!!

    private lateinit var loadingBinding: LoadingOverlayBinding

    private val homeDistributorViewModel: HomeDistributorViewModel by viewModel()
    private lateinit var adapter: ItemHomeDistributorAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDistributorBinding.inflate(inflater, container, false)
        loadingBinding = LoadingOverlayBinding.inflate(inflater)
        (binding.root as ViewGroup).addView(loadingBinding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ItemHomeDistributorAdapter()
        binding.recyclerViewDasboardDistributor.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.recyclerViewDasboardDistributor.adapter = adapter


        binding.logoutDistributorButton.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    homeDistributorViewModel.logout()
                    dialog.dismiss()
                }.setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        val mainNavHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment



        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeDistributorViewModel.isLogged.collectLatest {

                    if (!it) {
                        Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT)
                            .show()
                        mainNavHost.navController.navigate(DashboardDistributorFragmentDirections.actionDashboardDistributorFragmentToDistributorLoginFragment())
                    }
                }
            }
        }

        lifecycleScope.launch {

            homeDistributorViewModel.state.collectLatest { state ->
                when (state) {
                    is HomeDistributorState.Failure -> {
                        loadingBinding.loadingLayout.visibility = View.GONE
                        Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG)
                            .show()
                    }

                    HomeDistributorState.Initial -> {
                        loadingBinding.loadingLayout.visibility = View.GONE
                    }

                    HomeDistributorState.Loading -> {
                        loadingBinding.loadingLayout.visibility = View.VISIBLE
                    }

                    is HomeDistributorState.Success -> {
                        loadingBinding.loadingLayout.visibility = View.GONE

                        val namaDistributor =
                            state.dashboardResponse.namaDistributor.split(" ").firstOrNull() ?: ""
                        binding.headerTextDistributor.text = "Selamat datang $namaDistributor,"

                        binding.jumlahAgen.text = state.dashboardResponse.totalAgen.toString()
                        binding.omsetBulanDistributor.text =
                            state.dashboardResponse.totalPendapatan.toInt().toRupiah()
                        binding.totalStokDistributor.text =
                            state.dashboardResponse.finalStockKarton.toString()
                        binding.topProductNameDistributor.text =
                            state.dashboardResponse.topProductName
                        binding.jumlahSales.text =
                            state.dashboardResponse.totalSales.toString()
                        adapter.submitList(state.dashboardResponse.produkData)

                        val currentDate = LocalDate.now()
                        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
                        val formattedDate = currentDate.format(formatter)
                        binding.tanggalHariIni.text = formattedDate

                        val topName = state.dashboardResponse.topProductName
                        binding.topProductNameDistributor.text = topName

                        val topProduct = state.dashboardResponse.produkData.find {
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
                            .into(binding.topProductImageDistributor)

                        adapter.submitList(state.dashboardResponse.produkData)

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