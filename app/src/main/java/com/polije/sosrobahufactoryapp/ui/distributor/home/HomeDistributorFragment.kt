package com.polije.sosrobahufactoryapp.ui.distributor.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.dashboard.DashboardDistributorFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

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
            homeDistributorViewModel.logout()
        }

        lifecycleScope.launch {
            homeDistributorViewModel.isLogged.collectLatest {
                requireActivity().findNavController(R.id.fragmentContainerView).navigate(DashboardDistributorFragmentDirections.actionDashboardDistributorFragmentToDistributorLoginFragment())
            }
        }

        binding.tvlihatProdukTerlaris.setOnClickListener {
            val action =
                DashboardDistributorFragmentDirections.actionDashboardDistributorFragmentToStockDistributorRankFragment(
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}