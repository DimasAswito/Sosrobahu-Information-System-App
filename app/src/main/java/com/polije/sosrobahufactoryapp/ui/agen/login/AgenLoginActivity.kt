package com.polije.sosrobahufactoryapp.ui.agen.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.polije.sosrobahufactoryapp.databinding.ActivityAgenLoginBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AgenLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgenLoginBinding
    private val viewModel: AgenLoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgenLoginBinding.inflate(layoutInflater)
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