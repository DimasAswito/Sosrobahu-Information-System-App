package com.polije.sosrobahufactoryapp.ui.role

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.databinding.FragmentChooseRoleBinding

class ChooseRoleFragment : Fragment() {
    private var _binding: FragmentChooseRoleBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChooseRoleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvPabrik.setOnClickListener {
            val action = ChooseRoleFragmentDirections.actionChooseRoleFragmentToMobileNavigation()
            findNavController().navigate(action)
        }

        binding.cvDistributor.setOnClickListener {
            val action =
                ChooseRoleFragmentDirections.actionChooseRoleFragmentToDistributorNavigation()
            findNavController().navigate(action)
        }

        binding.cvAgen.setOnClickListener {
            val action = ChooseRoleFragmentDirections.actionChooseRoleFragmentToAgenNavigation()
            findNavController().navigate(action)
        }

        binding.cvSales.setOnClickListener {
            val action = ChooseRoleFragmentDirections.actionChooseRoleFragmentToSalesNavigation()
            findNavController().navigate(action)
        }

    }


}