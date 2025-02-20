package com.example.homework23.presentation.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.homework23.databinding.FragmentSplashBinding
import com.example.homework23.presentation.viewmodels.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentSplash : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate){
    private val authViewModel : ViewModel by viewModels()
    override fun setUp() {
        viewLifecycleOwner.lifecycleScope.launch {

//            authViewModel.authToken.collect { token ->
//                if (token.isNullOrEmpty()) {
//                    view?.findNavController()
//                       ?.navigate(FragmentSplashDirections.actionFragmentSplashToFragmentWelcome())
//                }
//                else {
//                    view?.findNavController()
//                        ?.navigate(FragmentSplashDirections.actionFragmentSplashToFragmentProfile())
//                }
//            }

        }
    }
}