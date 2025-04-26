package com.polije.sosrobahufactoryapp.ui.agen.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentAgenLoginBinding
import com.polije.sosrobahufactoryapp.ui.agen.login.AgenLoginViewModel
import com.polije.sosrobahufactoryapp.ui.agen.login.LoginState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue


class AgenLoginFragment : Fragment() {
    private var _binding: FragmentAgenLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AgenLoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgenLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
                        findNavController().navigate(R.id.action_agenLoginFragment_to_dashboardAgenFragment)
                    }

                    is LoginState.Error -> {
                        binding.progressBar4.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Login Failed: ${state.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.loginButton.isEnabled = true
                    }
                }
            }
        }
    }


}