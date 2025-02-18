package com.polije.sosrobahufactoryapp.ui.distributor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polije.sosrobahufactoryapp.R

class DistributorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distributor)
        supportActionBar?.hide()
    }
}