package com.polije.sosrobahufactoryapp.ui.distributor.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.dashboard.DashboardDistributorFragmentDirections
import com.polije.sosrobahufactoryapp.ui.distributor.home.component.ItemHomeDistributorAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeDistributorFragment : Fragment() {

    private var _binding: FragmentHomeDistributorBinding? = null
    private val binding get() = _binding!!
    private val homeDistributorViewModel: HomeDistributorViewModel by viewModel()
    private lateinit var adapter: ItemHomeDistributorAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDistributorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ItemHomeDistributorAdapter()
        binding.recyclerViewDasboardDistributor.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.recyclerViewDasboardDistributor.adapter = adapter


        binding.logoutDistributorButton.setOnClickListener {
            homeDistributorViewModel.logout()
        }

        val mainNavHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment



        lifecycleScope.launch {
            homeDistributorViewModel.isLogged.collect {
                if (!it) {
                    Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show()
                    mainNavHost.navController.navigate(DashboardDistributorFragmentDirections.actionDashboardDistributorFragmentToDistributorLoginFragment())
                }
            }
        }

        lifecycleScope.launch {

            homeDistributorViewModel.state.collectLatest { state ->
                when (state) {
                    is HomeDistributorState.Failure -> {
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG)
                            .show()
                    }

                    HomeDistributorState.Initial -> {
                        binding.progressBar2.visibility = View.GONE
                    }


                    HomeDistributorState.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE
                    }

                    is HomeDistributorState.Success -> {
                        binding.progressBar2.visibility = View.GONE
                        binding.jumlahAgen.text = state.dashboardResponse.totalAgen.toString()
                        binding.omsetBulanDistributor.text =
                            state.dashboardResponse.totalPendapatan.toInt()
                                .toRupiah()
                        binding.totalStokDistributor.text =
                            state.dashboardResponse.finalStockKarton.toString()
                        binding.topProductNameDistributor.text =
                            state.dashboardResponse.topProductName
                        adapter.submitList(state.dashboardResponse.produkData)

                        val topName = state.dashboardResponse.topProductName
                        binding.topProductNameDistributor.text = topName

                        val topProduct = state.dashboardResponse.produkData.find {
                            it.namaRokok == topName
                        }

                        val imageName = topProduct?.gambar
                        val imageUrl = BuildConfig.PICTURE_BASE_URL + "produk/" + imageName

                        val circularProgressDrawable = CircularProgressDrawable(requireContext()).apply {
                            strokeWidth = 5f
                            centerRadius = 30f
                            start()
                        }

                        Glide.with(requireContext())
                            .load(imageUrl)
                            .placeholder(circularProgressDrawable)
                            .error(R.drawable.foto_error)
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