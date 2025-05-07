package com.polije.sosrobahufactoryapp.ui.agen.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDashboardAgenBinding
import com.polije.sosrobahufactoryapp.databinding.FragmentDashboardDistributorBinding
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.setStatusBarColorByRole

class DashboardAgenFragment : Fragment() {
    private var _binding : FragmentDashboardAgenBinding? = null
    private val binding get()  = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardAgenBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setStatusBarColorByRole(UserRole.AGEN)

        val navHost = childFragmentManager.findFragmentById(binding.dashboardAgenContainerView.id) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHost.navController)


    }

}