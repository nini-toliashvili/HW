package com.example.homework23.fragments

import androidx.navigation.findNavController
import com.example.homework23.R
import com.example.homework23.databinding.FragmentWelcomePageBinding

class FragmentWelcome : BaseFragment<FragmentWelcomePageBinding> (FragmentWelcomePageBinding::inflate) {
    override fun setUp() {
        listeners()
    }

    private fun listeners() {
        binding.loginButtonHm.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_fragmentWelcome_to_fragmentLogin)
        }

        binding.registerButtonHm.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_fragmentWelcome_to_fragmentRegister)
        }
    }
}