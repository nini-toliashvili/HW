package com.example.homework16.fragments

import com.example.homework16.databinding.FragmentHomeBinding
import com.example.myapplication.BaseFragment

class HomeFragment:BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun setUp() {
        binding.emailDisplayed.setText("email logged in")
    }
}