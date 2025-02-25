package com.example.homework24.presentation.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework24.data.model.Story
import com.example.homework24.domain.usecase.GetPostsUseCase
import com.example.homework24.domain.usecase.GetStoriesUseCase
import com.example.homework24.presentation.user.mapper.toUiModel
import com.example.homework24.presentation.user.model.PostUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories: StateFlow<List<Story>> get() = _stories

    private val _posts = MutableStateFlow<List<PostUiModel>>(emptyList())
    val posts: StateFlow<List<PostUiModel>> get() = _posts


    fun getStories() {
        viewModelScope.launch { _stories.value = getStoriesUseCase() }
    }

    fun getPosts() {
        viewModelScope.launch {
            val postsList = getPostsUseCase().map { it.toUiModel() }
            _posts.value = postsList
        }
    }
}