package com.polije.sosrobahufactoryapp.ui.agen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeAgenBinding
import com.polije.sosrobahufactoryapp.ui.agen.dashboard.DashboardAgenFragmentDirections
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeAgenFragment : Fragment() {

    private var _binding: FragmentHomeAgenBinding? = null
    private val binding get() = _binding!!
    private val homeAgenViewModel: HomeAgenViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeAgenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.logoutAgenButton.setOnClickListener {

        }

//        binding.tvlihatProdukTerlaris.setOnClickListener {
//            val action =
//                DashboardAgenFragmentDirections.actionDashboardAgenFragmentToStockAgenRankFragment(
//                listStockProdukAgen
//                )
//            requireActivity().findNavController(R.id.fragmentContainerView).navigate(action)
//        }

        lifecycleScope.launch {
            homeAgenViewModel.isLogged.collectLatest {
                if (!it) {
                    Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show()
                    val action =
                        DashboardAgenFragmentDirections.actionDashboardAgenFragmentToAgenLoginFragment()
                    requireActivity().findNavController(R.id.fragmentContainerView).navigate(action)
                }
            }
        }
    }

    fun observeViewModel() {

        lifecycleScope.launch {

            homeAgenViewModel.state.collectLatest { state ->
                when (state) {
                    is HomeAgenState.Failure -> {
                        Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG)
                            .show()
                    }

                    HomeAgenState.Initial -> {
                        binding.progressBar2.visibility = View.GONE
                    }


                    HomeAgenState.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE
                    }

                    is HomeAgenState.Success -> {
                        binding.jumlahSales.text = state.dashboardResponse.totalSales.toString()
                        binding.omsetBulanAgen.text = state.dashboardResponse.totalPendapatan?.toRupiah()
                        binding.topProductNameAgen.text = state.dashboardResponse.topProduct

//                        val topName = state.dashboardResponse.topProduct
//                        binding.topProductNameAgen.text = topName
//
//                        val topProduct = state.dashboardResponse.produkData.find {
//                            it.namaRokok == topName
//                        }
//
//                        val imageName = topProduct?.gambar
//                        val imageUrl = BuildConfig.PICTURE_BASE_URL + "produk/" + imageName
//
//                        val circularProgressDrawable = CircularProgressDrawable(requireContext()).apply {
//                            strokeWidth = 5f
//                            centerRadius = 30f
//                            start()
//                        }
//
//                        Glide.with(requireContext())
//                            .load(imageUrl)
//                            .placeholder(circularProgressDrawable)
//                            .error(R.drawable.foto_error)
//                            .into(binding.topProductImageAgen)
//
//                        adapter.submitList(state.dashboardResponse.produkData)
                    }

                }
            }
        }
    }

    private fun navigateToLogin() {
//        val intent = Intent(requireContext(), FactoryLoginActivity::class.java)
//        startActivity(intent)
//        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}