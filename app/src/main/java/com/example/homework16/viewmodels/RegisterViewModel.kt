package com.example.homework16.viewmodels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework16.api.RegisterRequest
import com.example.homework16.api.RetrofitClient
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private val _registerStatus = MutableLiveData<String>()
    val registerStatus: LiveData<String> get() = _registerStatus

    fun registerUser( email: String,  password: String) {
        viewModelScope.launch {
            val response = RetrofitClient.instance.register(RegisterRequest(email, password))
            if (response.isSuccessful && response.body()?.success == true) {
                _registerStatus.postValue("registered successfully")
            } else _registerStatus.postValue("registration failed")
        }
    }
}