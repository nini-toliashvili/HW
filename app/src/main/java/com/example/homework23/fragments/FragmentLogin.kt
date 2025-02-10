package com.example.homework23.fragments

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.homework23.ViewModel
import com.example.homework23.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class FragmentLogin:BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private lateinit var authViewModel: ViewModel

    override fun setUp() {
        authViewModel = ViewModelProvider(this)[ViewModel::class.java]
        observe
    }

    private fun listeners() {
        binding.loginButtonL.setOnClickListener {

        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            authViewModel.loginState.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        showToast("Login successful: ${resource.data}")
                    }
                    is Resource.Error -> {
                        showToast("Error: ${resource.message}")
                    }
                    else -> Unit // Handle loading state if needed
                }
            }
        }
    }
}