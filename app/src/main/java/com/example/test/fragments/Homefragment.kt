package com.example.test.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.UserAdapter
import com.example.test.api.ApiService
import com.example.test.api.RetrofitClient
import com.example.test.data.AppDatabase
import com.example.test.data.UserDao
import com.example.test.databinding.FragmentHomeBinding
import com.example.test.viewmodels.HomeViewModel
import com.example.test.viewmodels.HomeViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Homefragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var userDao: UserDao
    private lateinit var apiService: ApiService
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userAdapter: UserAdapter

    override fun setUp() {
        val recyclerView = binding.recyclerView

        userAdapter = UserAdapter(emptyList())
        userDao = AppDatabase.getDatabase(requireContext().applicationContext).userDao()
        apiService = RetrofitClient.apiService
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(userDao, apiService)).get(HomeViewModel::class.java)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }



        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.loading.collect{ isLoading ->
                binding.loader.visibility = if(isLoading) View.VISIBLE else View.GONE

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.usersFlow.collectLatest { users ->
                binding.loader.visibility = View.GONE
                userAdapter.updateData(users)
            }
        }


        homeViewModel.refreshUsers()
    }
}