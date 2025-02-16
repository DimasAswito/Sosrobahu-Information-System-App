package com.polije.sosrobahufactoryapp.ui.factory.pengaturan

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentPengaturanBinding
import com.polije.sosrobahufactoryapp.databinding.FragmentPesananBinding
import com.polije.sosrobahufactoryapp.ui.factory.login.LoginActivity
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.PesananViewModel

class PengaturanFragment : Fragment() {

    private var _binding: FragmentPengaturanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPengaturanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupClickListeners()

        return root
    }

    private fun setupClickListeners() {
        val navController = findNavController()

        // Logout Button Click
        binding.logoutButton.setOnClickListener {
            showLogoutDialog()
        }

        // Navigasi ke RekeningFactoryFragment
        binding.containerRekeningBank.setOnClickListener {
            navController.navigate(R.id.action_navigation_pengaturan_to_rekeningFactoryFragment)
        }

        // Navigasi ke ListDistributorFragment
        binding.containerListDistributor.setOnClickListener {
            navController.navigate(R.id.action_navigation_pengaturan_to_listDistributorFragment)
        }

        // Navigasi ke AddDistributorFragment
        binding.containerAddDistributor.setOnClickListener {
            navController.navigate(R.id.action_navigation_pengaturan_to_addDistributorFragment)
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah anda yakin untuk Log out?")
            .setPositiveButton("Ya") { _, _ ->
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
