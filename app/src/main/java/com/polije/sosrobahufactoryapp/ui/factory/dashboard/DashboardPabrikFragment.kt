package com.polije.sosrobahufactoryapp.ui.factory.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.polije.sosrobahufactoryapp.databinding.FragmentDashboardPabrikBinding

class DashboardPabrikFragment : Fragment() {

    private var _binding: FragmentDashboardPabrikBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardPabrikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost = childFragmentManager.findFragmentById(binding.pabrikDashboardFragmentContainer.id) as NavHostFragment
        binding.dashboardBottomNavigation.setupWithNavController(navHost.navController)
    }


}