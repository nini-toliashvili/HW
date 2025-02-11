package com.example.homework23.fragments

import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.homework23.R
import com.example.homework23.viewmodels.ViewModel
import com.example.homework23.databinding.FragmentWelcomePageBinding
import kotlinx.coroutines.launch

class FragmentWelcome : BaseFragment<FragmentWelcomePageBinding> (FragmentWelcomePageBinding::inflate) {
    private val authViewModel by lazy { ViewModel(requireActivity().application) }
    override fun setUp() {
        listeners()
        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.authToken.collect { token ->
                if (token != null) {
                    view?.findNavController()?.navigate(FragmentWelcomeDirections.actionFragmentWelcomeToFragmentProfile())
                }
            }
        }
    }

    private fun listeners() {
        binding.loginButtonHm.setOnClickListener {
            view?.findNavController()?.navigate(FragmentWelcomeDirections.actionFragmentWelcomeToFragmentLogin())
        }

        binding.registerButtonHm.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_fragmentWelcome_to_fragmentRegister)
        }
    }
}