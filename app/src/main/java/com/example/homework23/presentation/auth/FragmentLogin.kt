package com.example.homework23.presentation.auth

import android.text.InputType
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.homework23.R
import com.example.homework23.data.Resource
import com.example.homework23.databinding.FragmentLoginBinding
import com.example.homework23.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentLogin : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val authViewModel: AuthViewModel by viewModels()

    override fun setUp() {


        parentFragmentManager.setFragmentResultListener(
            "requestKey",
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString("KEY_EMAIL") ?: ""
            val password = bundle.getString("KEY_PASSWORD") ?: ""
            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.usernameInputFieldLogin.setText(email)
                binding.passwordInputFieldLogin.setText(password)
            }
        }
        listeners()

    }

    private fun listeners() {
        binding.loginButtonL.setOnClickListener {
            val email = binding.usernameInputFieldLogin.text.toString()
            val password = binding.passwordInputFieldLogin.text.toString()
            val remember = binding.rememberMe.isChecked
            val isValid = authViewModel.validateUserInput(email, password)

            if (email.isNotEmpty() && password.isNotEmpty() && isValid) {
                authViewModel.login(email, password, remember)
                view?.findNavController()
                    ?.navigate(
                        FragmentLoginDirections.actionFragmentLoginToFragmentProfile(),
                        NavOptions.Builder()
                            .setPopUpTo(R.id.nav_graph, true)
                            .build()
                    )


            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter valid email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            observeViewModel()
        }

        binding.visibilityButton.setOnClickListener {
            passwordVisibility()
        }
    }

    private fun passwordVisibility() {
        if (binding.passwordInputFieldLogin.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            binding.passwordInputFieldLogin.inputType =
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.visibilityButton.background = R.drawable.eyeinvisiblefilled.toDrawable()
        } else {
            binding.passwordInputFieldLogin.inputType =
                InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.visibilityButton.background = R.drawable.eyefilled.toDrawable()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            authViewModel.loginState.collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Login successful: ${resource.data}",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error: ${resource.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> Unit
                }
            }
        }
    }
}