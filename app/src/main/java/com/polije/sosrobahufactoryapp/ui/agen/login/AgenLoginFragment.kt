package com.polije.sosrobahufactoryapp.ui.agen.login

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentAgenLoginBinding
import com.polije.sosrobahufactoryapp.databinding.LoadingOverlayBinding
import com.polije.sosrobahufactoryapp.utils.LoginState
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.setStatusBarColorByRole
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class AgenLoginFragment : Fragment() {
    private var _binding: FragmentAgenLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loadingBinding: LoadingOverlayBinding

    private val viewModel: AgenLoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgenLoginBinding.inflate(inflater, container, false)
        loadingBinding = LoadingOverlayBinding.bind(
            binding.root.findViewById(R.id.loadingLayout)
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_agenLoginFragment_to_chooseRoleFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        activity?.setStatusBarColorByRole(UserRole.AGEN)

        binding.btnBack.setOnClickListener { findNavController().navigate(R.id.action_agenLoginFragment_to_chooseRoleFragment) }
        binding.usernameEditText.doAfterTextChanged { text -> viewModel.onUsernameChanged(text.toString()) }
        binding.passwordEditText.doAfterTextChanged { text -> viewModel.onPasswordChanged(text.toString()) }

        lifecycleScope.launch {
            viewModel.isValid.collectLatest { isValid ->
                binding.loginButton.isEnabled = isValid
                val context = binding.loginButton.context
                val color = if (isValid) {
                    ContextCompat.getColor(context, R.color.agen_theme_dark)
                } else {
                    ContextCompat.getColor(context, android.R.color.darker_gray)
                }
                binding.loginButton.backgroundTintList = ColorStateList.valueOf(color)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isAlreadyLoggedIn.collectLatest {
                    if (it) {
                        val action =
                            AgenLoginFragmentDirections.actionAgenLoginFragmentToDashboardAgenFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            viewModel.login()
        }


        lifecycleScope.launch {
            viewModel.loginState.collectLatest { state ->
                when (state) {
                    is LoginState.Idle -> {
                        loadingBinding.loadingLayout.visibility = View.GONE

                    }

                    is LoginState.Loading -> {
                        loadingBinding.loadingLayout.visibility = View.VISIBLE
                        binding.loginButton.isEnabled = false
                    }

                    is LoginState.Success -> {
                        loadingBinding.loadingLayout.visibility = View.GONE
                        binding.loginButton.isEnabled = false
                    }

                    is LoginState.Error -> {
                        loadingBinding.loadingLayout.visibility = View.GONE
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