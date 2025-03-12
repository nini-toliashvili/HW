package com.example.homework23.presentation.auth

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.homework23.R
import com.example.homework23.databinding.FragmentWelcomePageBinding
import com.example.homework23.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentWelcome : BaseFragment<FragmentWelcomePageBinding>(FragmentWelcomePageBinding::inflate) {
    private val authViewModel : AuthViewModel by viewModels()
    override fun setUp() {
        listeners()

        viewLifecycleOwner.lifecycleScope.launch {
                if (authViewModel.isSessionSaved()) {
                    view?.findNavController()?.navigate(FragmentWelcomeDirections.actionFragmentWelcomeToFragmentProfile())
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