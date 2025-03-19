package com.example.homework25.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework25.common.Resource
import com.example.homework25.domain.usecase.GetSearchedCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getSearchedCategoriesUseCase: GetSearchedCategoriesUseCase
) : ViewModel(){

    init {
        processEvent()
    }

    private val _state = MutableStateFlow<CategoriesState>(CategoriesState.Idle)
    val state: StateFlow<CategoriesState> get() = _state.asStateFlow()

    private val _userEvents = MutableSharedFlow<CategoriesEvent>()


    private fun processEvent() {
        viewModelScope.launch {
            _userEvents.debounce(300)
                .collect{event ->
                    when (event) {
                        is CategoriesEvent.Search -> searchCategories(event.query)
                    }
                }
        }
    }

    fun sendEvent(event: CategoriesEvent) {
        viewModelScope.launch {
            _userEvents.emit(event)
        }
    }

    private fun searchCategories(query : String) {
        viewModelScope.launch {
            getSearchedCategoriesUseCase.invoke(query).collect{ resource ->
                when (resource) {
                    is Resource.Loader -> _state.value = CategoriesState.Loading
                    is Resource.Success -> {
                        if (resource.data.isEmpty()) {
                            _state.value = CategoriesState.NoMatchesFound
                        } else {
                            _state.value = CategoriesState.Success(resource.data)
                        }
                    }
                    is Resource.Error -> _state.value = CategoriesState.Error(resource.message)
                }
            }
        }
    }

}