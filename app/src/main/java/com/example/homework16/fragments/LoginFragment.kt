package com.example.homework16.fragments

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.homework16.databinding.FragmentLoginBinding
import com.example.homework16.viewmodels.LoginViewModel
import com.example.myapplication.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewmodel : LoginViewModel by viewModels()
    override fun setUp() {
        val email = binding.usernameInputFieldLogin.text.toString()
        val password = binding.passwordInputFieldLogin.text.toString()


        binding.loginButtonL.setOnClickListener {

            if (email != "" && password != "") {
                viewmodel.loginUser(email, password)
                viewmodel.loginStatus.observe(viewLifecycleOwner) { loggedIn ->
                    Toast.makeText(requireContext(), loggedIn, Toast.LENGTH_SHORT).show()
                }

            }
       // findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }

        binding.registerTextButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }


}