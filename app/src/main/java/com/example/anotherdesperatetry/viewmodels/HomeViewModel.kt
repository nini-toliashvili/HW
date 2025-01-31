package com.example.anotherdesperatetry.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.anotherdesperatetry.data.UserProfileRepository
import com.example.anotherdesperatetry.data.userProfileDataStore

class HomeViewModel(app: Application): AndroidViewModel(app) {
private val repository = UserProfileRepository(app.userProfileDataStore)
    val userProfileFlow = repository.userProfileFlow

    suspend fun saveUser(firstname:String, lastname:String, email:String){
        repository.saveUserProfile(firstname, lastname, email)
    }

}