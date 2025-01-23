package com.example.homework16.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework16.api.LoginRequest
import com.example.homework16.api.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _loginStatus = MutableLiveData<String>()
    val loginStatus: LiveData<String> get() = _loginStatus

    fun loginUser( email: String,  password: String) {
        viewModelScope.launch {
            val response = RetrofitClient.instance.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null && response.body()?.success == true) {
                _loginStatus.value = ("logged in successfully")
            } else {
                _loginStatus.value = ("authentication failed ${response.message()}")

            }
        }
    }
}