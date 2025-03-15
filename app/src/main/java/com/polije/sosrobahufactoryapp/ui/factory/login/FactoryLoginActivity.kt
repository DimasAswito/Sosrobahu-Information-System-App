package com.polije.sosrobahufactoryapp.ui.factory.login


import androidx.lifecycle.lifecycleScope
import com.polije.sosrobahufactoryapp.ui.factory.FactoryActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.polije.sosrobahufactoryapp.databinding.ActivityLoginFactoryBinding
import com.polije.sosrobahufactoryapp.ui.factory.FactoryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FactoryLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFactoryBinding
    private val viewModel by viewModels<FactoryLoginViewModel> {
        FactoryViewModel.Factory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginFactoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (viewModel.getToken() != null) {
            navigateToHome()
            return
        }

        lifecycleScope.launch {
            viewModel.loginResult.collectLatest { result ->
                if (result == "success") {
                    navigateToHome()
                }
            }
        }

        binding.passwordEditText.addTextChangedListener(textWatcher)
        binding.usernameEditText.addTextChangedListener(textWatcher)

        binding.loginButtonPabrik.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(username, password)
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, FactoryActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
        finish()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            binding.loginButtonPabrik.isEnabled = username.isNotEmpty() && password.isNotEmpty()
        }
    }
}

