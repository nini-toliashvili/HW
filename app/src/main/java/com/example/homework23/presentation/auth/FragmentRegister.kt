package com.example.homework23.presentation.auth

import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.homework23.R
import com.example.homework23.data.Resource
import com.example.homework23.databinding.FragmentRegisterBinding
import com.example.homework23.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentRegister : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val authViewModel : AuthViewModel by viewModels()
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

        binding.visibilityButtonRegister1.setOnClickListener {
            passwordVisibility1()
        }

        binding.visibilityButtonRegister2.setOnClickListener {
            passwordVisibility2()
        }
    }

    private fun passwordVisibility1() {

        val password = binding.passwordInputFieldRegister1.text.toString()
        val passwordRepeat = binding.passwordInputFieldRegister2.text.toString()
        if (binding.passwordInputFieldRegister1.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD && password == passwordRepeat) {
            binding.passwordInputFieldRegister1.inputType =
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.visibilityButtonRegister1.background = R.drawable.eyeinvisiblefilled.toDrawable()
        } else {
            binding.passwordInputFieldRegister1.inputType =
                InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.visibilityButtonRegister1.background = R.drawable.eyefilled.toDrawable()
        }
    }

    private fun passwordVisibility2() {

        val password = binding.passwordInputFieldRegister1.text.toString()
        val passwordRepeat = binding.passwordInputFieldRegister2.text.toString()
        if (binding.passwordInputFieldRegister2.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD && password == passwordRepeat) {
            binding.passwordInputFieldRegister2.inputType =
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.visibilityButtonRegister2.background = R.drawable.eyeinvisiblefilled.toDrawable()
        } else {
            binding.passwordInputFieldRegister2.inputType =
                InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.visibilityButtonRegister2.background = R.drawable.eyefilled.toDrawable()
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