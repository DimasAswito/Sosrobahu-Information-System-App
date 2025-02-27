package com.polije.sosrobahufactoryapp.ui.factory.pengaturan.tambahAkun

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.polije.sosrobahufactoryapp.databinding.FragmentAddDistributorBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddDistributorFragment : Fragment() {


    private val viewModel: AddDistributorViewModel by viewModels()

    private var _binding: FragmentAddDistributorBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDistributorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edFullName.textChangedListener(viewModel::onFullNameChanged)
        binding.edUsername.textChangedListener(viewModel::onUsernameChanged)
        binding.edPassword.textChangedListener(viewModel::onPasswordChanged)
        binding.edPhoneNumber.textChangedListener(viewModel::onPhoneNumberChanged)
        binding.edNIK.textChangedListener(viewModel::onNIKChanged)

        lifecycleScope.launch {
            viewModel.isValid.collectLatest { state ->
                binding.btnSave.isEnabled = state
            }
        }
    }


    private fun TextInputEditText.textChangedListener(function: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                function(s.toString())
            }
        })
    }

}