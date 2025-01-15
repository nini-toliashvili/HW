package com.example.homework15


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class SharedViewModel : ViewModel() {
    private val _currentCards = MutableLiveData<List<Card>>()

    init {
        _currentCards.value = mutableListOf(
            Card(UUID.randomUUID(), "Sunie pham", "4364  1345  8932  8378", "04/13", 999, true),
            Card(UUID.randomUUID(), "Saba Jikurashvili", "4364  1345  8932  8378", "04/23", 999, false),
            Card(UUID.randomUUID(), "Nino Toliashvili", "4364  1345  8932  8378", "04/08", 999, true)
        )
    }


    val currentCards : MutableLiveData<List<Card>> get() = _currentCards
    fun addCard(card:Card) {
        val currentList = _currentCards.value?.toMutableList() ?: mutableListOf()
        currentList.add(0, card)
        _currentCards.value = currentList
    }

    fun removeCard(position:Int) {
        val currentList = _currentCards.value?.toMutableList() ?: mutableListOf()

        currentList.removeAt(position)
        _currentCards.value = currentList
    }

}