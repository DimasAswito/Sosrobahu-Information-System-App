package com.polije.sosrobahufactoryapp.ui.sales.home

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
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeSalesBinding
import com.polije.sosrobahufactoryapp.ui.agen.dashboard.DashboardAgenFragmentDirections
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeSalesFragment : Fragment() {

    private var _binding: FragmentHomeSalesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeSalesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeSalesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLogged.collectLatest {
                    if (!it) {
                        Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT)
                            .show()
                        val action =
                            DashboardAgenFragmentDirections.actionDashboardAgenFragmentToAgenLoginFragment()
                        activity?.findNavController(R.id.fragmentContainerView)
                            ?.navigate(action)
                    }
                }
            }


        }

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is HomeSalesState.Failure -> {
                        Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG)
                            .show()
                    }

                    HomeSalesState.Initial -> {
                        binding.progressBar2.visibility = View.GONE
                    }


                    HomeSalesState.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE
                    }

                    is HomeSalesState.Success -> {

//                        binding.totalStokSales.text = state.dashboardResponse.totalStokKeseluruhan.toString()
//                        binding.jumlahToko.text   = state.dashboardResponse.jumlahToko.toString()
//                        binding.modalSales.text =
//                            state.dashboardResponse.totalPendapatan?.toRupiah()
//
//                        binding.imgTopProduct. // tak binding sendiri ngko lur
//                        binding.topProductNameSales.text = state.dashboardResponse.topProduct

                    }

                }
            }
        }
    }
}