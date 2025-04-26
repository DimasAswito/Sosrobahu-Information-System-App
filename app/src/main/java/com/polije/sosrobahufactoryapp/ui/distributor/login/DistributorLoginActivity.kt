package com.polije.sosrobahufactoryapp.ui.distributor.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polije.sosrobahufactoryapp.databinding.ActivityLoginDistributorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DistributorLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginDistributorBinding
    private val viewModel: DistributorLoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginDistributorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }


}