package com.polije.sosrobahufactoryapp.ui.distributor.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.polije.sosrobahufactoryapp.databinding.ActivityLoginDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.DistributorActivity

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DistributorLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginDistributorBinding
    private val viewModel: DistributorLoginViewModel by viewModel()
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

        binding.loginButton.setOnClickListener {
            viewModel.login()
        }

        lifecycleScope.launch {
            viewModel.loginState.collectLatest { state ->
                when (state) {
                    is LoginState.Idle -> {
                        binding.progressBar4.visibility = View.GONE

                    }

                    is LoginState.Loading -> {
                        binding.progressBar4.visibility = View.VISIBLE
                        binding.loginButton.isEnabled = false
                    }

                    is LoginState.Success -> {
                        binding.progressBar4.visibility = View.GONE
                        binding.loginButton.isEnabled = false
                        navigateToHome()
                    }

                    is LoginState.Error -> {
                        binding.progressBar4.visibility = View.GONE
                        Toast.makeText(
                            this@DistributorLoginActivity,
                            "Login Failed: ${state.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.loginButton.isEnabled = true
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, DistributorActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
        finish()
    }
}