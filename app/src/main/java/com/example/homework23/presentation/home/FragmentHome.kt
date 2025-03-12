package com.example.homework23.presentation.home


import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework23.presentation.UserAdapter
import com.example.homework23.presentation.auth.AuthViewModel
import com.example.homework23.databinding.FragmentProfileBinding
import com.example.homework23.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentHome : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val authViewModel : AuthViewModel by viewModels()
    private val usersViewModel: UsersViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun setUp() {
        listeners()
        setUpAdapter()
    }

    private fun listeners() {
        binding.logOutButtonL.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        authViewModel.logOut()
        view?.findNavController()?.navigate(FragmentHomeDirections.actionFragmentProfileToFragmentLogin())
    }

    private fun setUpAdapter() {
        userAdapter = UserAdapter()
        binding.loader.visibility = View.VISIBLE
        val recyclerView : RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = userAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            usersViewModel.users.collectLatest { pagingData ->
                userAdapter.submitData(pagingData)
            }
        }
        binding.loader.visibility = View.GONE

    }

}