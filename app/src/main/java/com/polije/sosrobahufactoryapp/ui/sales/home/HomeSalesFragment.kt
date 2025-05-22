package com.polije.sosrobahufactoryapp.ui.sales.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeSalesBinding
import com.polije.sosrobahufactoryapp.databinding.LoadingOverlayBinding
import com.polije.sosrobahufactoryapp.ui.sales.dashboard.DashboardSalesFragmentDirections
import com.polije.sosrobahufactoryapp.ui.sales.home.component.ItemHomeSalesAdapter
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeSalesFragment : Fragment() {

    private var _binding: FragmentHomeSalesBinding? = null
    private val binding get() = _binding!!

    private lateinit var loadingBinding: LoadingOverlayBinding

    private val viewModel: HomeSalesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeSalesBinding.inflate(inflater, container, false)
        loadingBinding = LoadingOverlayBinding.inflate(inflater)
        (binding.root as ViewGroup).addView(loadingBinding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemHomeSalesAdapter()
        binding.recyclerViewDasboardSales.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewDasboardSales.adapter = adapter

        binding.logoutSalesButton.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    viewModel.logout()
                    dialog.dismiss()
                }.setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLogged.collectLatest {
                    if (!it) {
                        Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT)
                            .show()
                        val action =
                            DashboardSalesFragmentDirections.actionDashboardSalesFragmentToSalesLoginFragment()
                        activity?.findNavController(R.id.fragmentContainerView)?.navigate(action)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->

                loadingBinding.loadingLayout.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                state.errorMessage?.let {
                    loadingBinding.loadingLayout.visibility = View.GONE
                    Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG)
                        .show()
                }

                state.dashboard?.let {
                    loadingBinding.loadingLayout.visibility = View.GONE

                    val namaSales =
                        state.dashboard.namaSales?.split(" ")?.firstOrNull() ?: ""
                    binding.headerTextSales.text = "Selamat datang $namaSales,"

                    binding.totalStokSales.text = state.dashboard?.totalStok.toString()
                    binding.jumlahToko.text = state.dashboard?.jumlahToko.toString()
                    binding.modalSales.text = state.dashboard?.totalPrice?.toRupiah()
                    binding.topProductNameSales.text = state.dashboard?.topProduct
                }
 
                state.barangReponse.let { listBarang ->
                    adapter.submitList(listBarang?.listBarangAgen)
                }


//                                is HomeSalesState.Failure -> {
//                        loadingBinding.loadingLayout.visibility = View.GONE
//                        Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG)
//                            .show()
//                    }
//
//                    HomeSalesState.Initial -> {
//                        loadingBinding.loadingLayout.visibility = View.GONE
//                    }
//
//                    HomeSalesState.Loading -> {
//                        loadingBinding.loadingLayout.visibility = View.VISIBLE
//                    }
//
//                    is HomeSalesState.Success -> {
//                        loadingBinding.loadingLayout.visibility = View.GONE
//
//                        val namaSales =
//                            state.dashboardResponse.namaSales?.split(" ")?.firstOrNull() ?: ""
//                        binding.headerTextSales.text = "Selamat datang $namaSales,"
//
//                        binding.totalStokSales.text = state.dashboardResponse.totalStok.toString()
//                        binding.jumlahToko.text = state.dashboardResponse.jumlahToko.toString()
//                        binding.modalSales.text = state.dashboardResponse.totalPrice?.toRupiah()
//                        binding.topProductNameSales.text = state.dashboardResponse.topProduct
//                    }

            }
        }
    }
}