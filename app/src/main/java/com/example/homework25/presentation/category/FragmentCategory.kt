package com.example.homework25.presentation.category

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework25.databinding.FragmentCategoryBinding
import com.example.homework25.presentation.BaseFragment
import com.example.homework25.presentation.mapper.toPresentation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentCategory : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {
    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var categoriesAdapter: CategoryListAdapter
    override fun setUp() {
        categoriesAdapter = CategoryListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = categoriesAdapter

        observeOnSearchInput()
        observeOnState()

    }

    private fun observeOnSearchInput() {
        binding.searchField.addTextChangedListener { editable ->
            editable?.let { query ->
                viewModel.sendEvent(CategoriesEvent.Search(query.toString()))
            }
        }

    }

    private fun observeOnState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is CategoriesState.Loading -> {}
                        is CategoriesState.Success -> {
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.noMatchesTv.visibility = View.GONE
                            categoriesAdapter.submitList(state.categories.map { it.toPresentation() })
                        }
                        is CategoriesState.Error -> {}
                        is CategoriesState.Idle -> {}
                        is CategoriesState.NoMatchesFound -> {
                            binding.noMatchesTv.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }


}