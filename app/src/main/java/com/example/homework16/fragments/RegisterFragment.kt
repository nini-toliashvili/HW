package com.example.homework16.fragments

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.homework16.api.RegisterRequest
import com.example.homework16.api.RetrofitClient
import com.example.homework16.databinding.FragmentRegisterBinding
import com.example.homework16.viewmodels.RegisterViewModel
import com.example.myapplication.BaseFragment

class RegisterFragment :BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewmodel :RegisterViewModel by viewModels()
    override fun setUp() {
        binding.registerButtonR.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = if (binding.passwordInputFieldRegistration.text.toString() == binding.passwordInputFieldRepeat.text.toString()) binding.passwordInputFieldRepeat.text.toString()
            else ""
            if (email != "" && password != "") {
                viewmodel.registerUser(email, password)
                viewmodel.registerStatus.observe(viewLifecycleOwner) {isRegistered ->
                    Toast.makeText(requireContext(), isRegistered, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




}