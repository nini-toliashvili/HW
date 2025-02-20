package com.example.homework23.data

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}

