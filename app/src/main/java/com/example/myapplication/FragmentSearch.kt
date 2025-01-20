package com.example.myapplication

import com.example.myapplication.databinding.FragmentSearchBinding

class FragmentSearch:BaseFragment<FragmentSearchBinding> (FragmentSearchBinding::inflate) {

    private lateinit var viewModel: SharedViewModel
    private lateinit var adapter: ListAdapterChats
    override fun setUp() {
        TODO("Not yet implemented")
    }
}