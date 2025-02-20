package com.example.homework23.presentation.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.homework23.presentation.viewmodels.ViewModel
import com.example.homework23.data.Resource
import com.example.homework23.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentRegister : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val authViewModel : ViewModel by viewModels()
    override fun setUp() {
        listeners()
    }

    private fun listeners() {
        binding.registerButtonR.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.passwordInputFieldRegister1.text.toString()
            val passwordRepeat = binding.passwordInputFieldRegister2.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty() && password == passwordRepeat) {
                authViewModel.register(email, password)

                val resultBundle = Bundle().apply {
                    putString("KEY_EMAIL", email)
                    putString("KEY_PASSWORD", password)
                }
                setFragmentResult("requestKey", resultBundle)

                view?.findNavController()
                    ?.navigate(FragmentRegisterDirections.actionFragmentRegisterToFragmentLogin())

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
            authViewModel.registerState.collectLatest { resource ->
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