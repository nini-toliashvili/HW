package com.example.homework23.data

data class loginRequest(
    val email: String,
    val password: String
)

data class loginResponse(
    val token: String
)

data class registerRequest(
    val email:String,
    val password: String
)

data class registerResponse(
    val id: Int,
    val token: String
)

