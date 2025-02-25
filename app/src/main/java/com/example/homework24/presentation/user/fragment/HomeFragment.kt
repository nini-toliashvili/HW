package com.example.homework24.presentation.user.fragment

import android.util.Log
import com.example.homework24.databinding.FragmentHomeBinding
import com.example.homework24.presentation.user.viewmodel.ViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework24.presentation.user.adapter.PostAdapter
import com.example.homework24.presentation.user.adapter.StoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: ViewModel by viewModels()
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var postAdapter: PostAdapter
    override fun setUp() {
        viewModel.getStories()
        viewModel.getPosts()
        setUpStoryAdapter()
        setUpPostAdapter()

    }

    private fun setUpStoryAdapter() {
        storyAdapter = StoryAdapter()
        val recyclerView: RecyclerView = binding.storyRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = storyAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stories.collect { stories ->
                Log.d("StoryAdapter", "Received stories: ${stories.size}")
                storyAdapter.setStories(stories)

            }
        }
    }


    private fun setUpPostAdapter() {
        postAdapter = PostAdapter()
        val recyclerViewP: RecyclerView = binding.postRecyclerview
        recyclerViewP.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewP.adapter = postAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.posts.collect { posts ->
                Log.d("PostAdapter", "Received stories: ${posts.size}")
                postAdapter.submitList(posts.toList())

            }
        }
    }

}