package com.polije.sosrobahufactoryapp.ui.sales.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDashboardAgenBinding
import com.polije.sosrobahufactoryapp.databinding.FragmentDashboardSalesBinding
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.setStatusBarColorByRole

class DashboardSalesFragment : Fragment() {

    private var _binding : FragmentDashboardSalesBinding? = null
    private val binding get()  = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardSalesBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setStatusBarColorByRole(UserRole.SALES)

        val navHost = childFragmentManager.findFragmentById(binding.dashboardSalesContainerView.id) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHost.navController)
    }
}