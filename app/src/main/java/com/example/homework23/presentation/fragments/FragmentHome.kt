package com.example.homework23.presentation.fragments


import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework23.UserAdapter
import com.example.homework23.presentation.viewmodels.ViewModel
import com.example.homework23.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentHome : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val authViewModel : ViewModel by viewModels()
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
            authViewModel.users.collectLatest { pagingData ->
                userAdapter.submitData(pagingData)
            }
        }
        binding.loader.visibility = View.GONE

    }

}