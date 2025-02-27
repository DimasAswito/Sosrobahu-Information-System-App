package com.polije.sosrobahufactoryapp.ui.factory.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.polije.sosrobahufactoryapp.databinding.ActivityLoginFactoryBinding
import com.polije.sosrobahufactoryapp.ui.factory.FactoryActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FactoryLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFactoryBinding

    private val viewModel by viewModels<FactoryLoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginFactoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        lifecycleScope.launch {
            viewModel.isButtonEnabled.collectLatest {
                isButtonEnabled(it)
            }
        }


        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.onPasswordChanged(s.toString())
            }
        })

        binding.usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.onUsernameChanged(s.toString())
            }
        })

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, FactoryActivity::class.java)
                .apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            startActivity(intent)
            finish()

        }
    }

    private fun isButtonEnabled(isEnabled: Boolean) {
        binding.loginButton.isEnabled = isEnabled
    }
}
