package com.polije.sosrobahufactoryapp.ui.factory.login

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentFactoryLoginBinding
import com.polije.sosrobahufactoryapp.databinding.LoadingOverlayBinding
import com.polije.sosrobahufactoryapp.utils.LoginState
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.setStatusBarColorByRole
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FactoryLoginFragment : Fragment() {

    private var _binding: FragmentFactoryLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loadingBinding: LoadingOverlayBinding

    private val viewModel: FactoryLoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFactoryLoginBinding.inflate(inflater, container, false)
        loadingBinding = LoadingOverlayBinding.bind(
            binding.root.findViewById(R.id.loadingLayout)
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_login_pabrik_to_chooseRoleFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        activity?.setStatusBarColorByRole(UserRole.PABRIK)

        binding.btnBack.setOnClickListener { findNavController().navigate(R.id.action_login_pabrik_to_chooseRoleFragment) }
        binding.usernameEditText.doAfterTextChanged { text -> viewModel.onUsernameChanged(text.toString()) }
        binding.passwordEditText.doAfterTextChanged { text -> viewModel.onPasswordChanged(text.toString()) }

        binding.loginButtonPabrik.setOnClickListener {
            viewModel.login()
        }

        lifecycleScope.launch {

            launch {
                viewModel.loginState.collectLatest { state ->
                    when (state) {
                        is LoginState.Idle -> {
                            loadingBinding.loadingLayout.visibility = View.GONE
                        }

                        is LoginState.Loading -> {
                            loadingBinding.loadingLayout.visibility = View.VISIBLE
                            binding.loginButtonPabrik.isEnabled = false
                        }

                        is LoginState.Success -> {
                            loadingBinding.loadingLayout.visibility = View.GONE
                            binding.loginButtonPabrik.isEnabled = false
                        }

                        is LoginState.Error -> {
                            loadingBinding.loadingLayout.visibility = View.GONE
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

            launch {
                viewModel.isAlreadyLoggedIn.collect {
                    if (it) {
                        findNavController().navigate(R.id.action_login_pabrik_to_dashboardFragment)
                    }
                }
            }

            lifecycleScope.launch {
                viewModel.isValid.collectLatest { isValid ->
                    binding.loginButtonPabrik.isEnabled = isValid
                    val context = binding.loginButtonPabrik.context
                    val color = if (isValid) {
                        ContextCompat.getColor(context, R.color.factory_theme_dark)
                    } else {
                        ContextCompat.getColor(context, android.R.color.darker_gray)
                    }
                    binding.loginButtonPabrik.backgroundTintList = ColorStateList.valueOf(color)
                }
            }
        }
    }
}