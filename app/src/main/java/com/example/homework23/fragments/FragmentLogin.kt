package com.example.homework23.fragments

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.homework23.viewmodels.ViewModel
import com.example.homework23.data.Resource
import com.example.homework23.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentLogin : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val authViewModel by lazy { ViewModel(requireActivity().application) }

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


            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(email, password)
                view?.findNavController()
                    ?.navigate(FragmentLoginDirections.actionFragmentLoginToFragmentProfile())


            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter valid email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            observeViewModel()
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