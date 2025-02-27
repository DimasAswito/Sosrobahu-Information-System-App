package com.polije.sosrobahufactoryapp.ui.factory

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.ActivityMainBinding

class FactoryActivity : AppCompatActivity() {

    fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }

    fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = navHost.navController

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (setOf(
                    R.id.navigation_home,
                    R.id.navigation_pesanan,
                    R.id.navigation_riwayat,
                    R.id.navigation_harga,
                    R.id.navigation_pengaturan
                ).contains(destination.id)
            ) {
                showBottomNav()
            } else {

                hideBottomNav()
            }
        }
    }
}