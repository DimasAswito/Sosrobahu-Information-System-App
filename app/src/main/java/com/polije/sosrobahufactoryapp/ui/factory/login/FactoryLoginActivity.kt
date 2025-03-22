package com.polije.sosrobahufactoryapp.ui.factory.login


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.polije.sosrobahufactoryapp.databinding.ActivityLoginFactoryBinding
import com.polije.sosrobahufactoryapp.ui.factory.FactoryActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FactoryLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFactoryBinding
    private val viewModel: FactoryLoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginFactoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.usernameEditText.doAfterTextChanged { text -> viewModel.onUsernameChanged(text.toString()) }
        binding.passwordEditText.doAfterTextChanged { text -> viewModel.onPasswordChanged(text.toString()) }

        binding.loginButtonPabrik.setOnClickListener {
            viewModel.login()
        }

        lifecycleScope.launch {
            viewModel.isValid.collectLatest { binding.loginButtonPabrik.isEnabled = it }
        }

        lifecycleScope.launch {
            viewModel.loginState.collectLatest { state ->
                when (state) {
                    is LoginState.Idle -> {
                        binding.progressBar.visibility = View.GONE
                        binding.loginButtonPabrik.isEnabled = true
                    }
                    is LoginState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.loginButtonPabrik.isEnabled = false
                    }
                    is LoginState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.loginButtonPabrik.isEnabled = false
                    }
                    is LoginState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@FactoryLoginActivity, "Login Failed: ${state.message}", Toast.LENGTH_SHORT).show()
                        binding.loginButtonPabrik.isEnabled = true
                    }
                }
            }
        }



        lifecycleScope.launch {

        }


    }

    private fun navigateToHome() {
        val intent = Intent(this, FactoryActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
        finish()
    }

}

