package com.polije.sosrobahufactoryapp.ui.distributor.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeBinding
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.dashboard.DashboardDistributorFragmentDirections
import com.polije.sosrobahufactoryapp.ui.factory.dashboard.DashboardPabrikFragmentDirections
import com.polije.sosrobahufactoryapp.ui.factory.home.HomePabrikState
import com.polije.sosrobahufactoryapp.ui.factory.home.HomePabrikViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class HomeDistributorFragment : Fragment() {

    private var _binding: FragmentHomeDistributorBinding? = null
    private val binding get() = _binding!!
    private val homeDistributorViewModel: HomeDistributorViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDistributorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.logoutDistributorButton.setOnClickListener {

            Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        binding.tvlihatProdukTerlaris.setOnClickListener {
            val action = DashboardDistributorFragmentDirections.actionDashboardDistributorFragmentToStockDistributorRankFragment(
//                listStockProdukDistributor
            )


            requireActivity().findNavController(R.id.fragmentContainerView).navigate(action)
        }

    }

     fun observeViewModel() {

        lifecycleScope.launch {

//            homeDistributorViewModel.state.collectLatest { state ->
//                when (state) {
//                    is HomeDistributorState.Failure -> {
//                        Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG)
//                            .show()
//                    }
//
//                    HomeDistributorState.Initial -> {
//                        binding.progressBar2.visibility = View.GONE
//                    }
//
//
//                    HomeDistributorState.Loading -> {
//                        binding.progressBar2.visibility = View.VISIBLE
//                    }
//
//                    is HomeDistributorState.Success -> {

//                    Do something
//                    }
//
//                }
//            }
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