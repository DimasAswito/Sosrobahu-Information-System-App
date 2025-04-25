package com.polije.sosrobahufactoryapp.ui.distributor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.ActivityDistributorBinding

class DistributorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDistributorBinding

    //    fun hideBottomNav() {
//        binding.navView.visibility = View.GONE
//    }
//
//    fun showBottomNav() {
//        binding.navView.visibility = View.VISIBLE
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDistributorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_distributor) as NavHostFragment

        val navController = navHost.navController

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (setOf(
                    R.id.homeDistributorFragment,
                    R.id.pesananDistributorFragment,
                    R.id.orderDistributorFragment,

                    ).contains(destination.id)
            ) {

            } else {

                //hideBottomNav()
            }
        }
    }
}