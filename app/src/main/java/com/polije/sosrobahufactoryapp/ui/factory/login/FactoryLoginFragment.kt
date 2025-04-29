package com.polije.sosrobahufactoryapp.ui.factory.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentFactoryLoginBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FactoryLoginFragment : Fragment() {

    private var _binding: FragmentFactoryLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FactoryLoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFactoryLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usernameEditText.doAfterTextChanged { text -> viewModel.onUsernameChanged(text.toString()) }
        binding.passwordEditText.doAfterTextChanged { text -> viewModel.onPasswordChanged(text.toString()) }

        binding.loginButtonPabrik.setOnClickListener {
            viewModel.login()
        }

        lifecycleScope.launch {
            viewModel.isValid.collectLatest { binding.loginButtonPabrik.isEnabled = it }
        }

        lifecycleScope.launch {
            viewModel.isAlreadyLoggedIn.collect {
                if (it) {
                    findNavController().navigate(R.id.action_login_pabrik_to_dashboardFragment)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.loginState.collectLatest { state ->
                when (state) {
                    is LoginState.Idle -> {
                        binding.progressBar.visibility = View.GONE
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
                        Toast.makeText(
                            requireContext(),
                            "Login Failed: ${state.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.loginButtonPabrik.isEnabled = true
                    }
                }
            }
        }
    }
}