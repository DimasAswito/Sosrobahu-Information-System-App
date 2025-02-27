package com.polije.sosrobahufactoryapp.ui.distributor.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.polije.sosrobahufactoryapp.databinding.ActivityLoginDistributorBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DistributorLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginDistributorBinding
    private val viewModel: DistributorLoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginDistributorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.usernameEditText.doAfterTextChanged { text -> viewModel.onUsernameChanged(text.toString()) }
        binding.passwordEditText.doAfterTextChanged { text -> viewModel.onPasswordChanged(text.toString()) }


        lifecycleScope.launch {
            viewModel.isValid.collectLatest {
                binding.loginButton.isEnabled = it
            }
        }
    }
}