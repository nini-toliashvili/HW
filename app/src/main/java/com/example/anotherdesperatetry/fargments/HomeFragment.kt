package com.example.anotherdesperatetry.fargments

import BaseFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.codelab.android.datastore.UserProfile
import com.example.anotherdesperatetry.databinding.FragmentHomeBinding
import com.example.anotherdesperatetry.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }
    override fun setUp() {


        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {
                homeViewModel.saveUser(
                    binding.firstnameEt.text.toString(),
                    binding.lastnameEt.text.toString(),
                    binding.emailEt.text.toString(),
                )
            }

            binding.firstnameEt.setText("")
            binding.lastnameEt.setText("")
            binding.emailEt.setText("")
        }


        var firstname =""
        var lastname = ""
        var email = ""

        lifecycleScope.launch {
            homeViewModel.userProfileFlow.collectLatest { UserProfile ->
                firstname = UserProfile.firstname
                lastname = UserProfile.lastname
                email  = UserProfile.email
            }

        }

        binding.readButton.setOnClickListener {
            binding.firstnameTv.text
            binding.firstnameTv.setText( firstname)
            binding.lastnameTv.setText(lastname)
            binding.emailTv.setText(email)

        }
    }

}