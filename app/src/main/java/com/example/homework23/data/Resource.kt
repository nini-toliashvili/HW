package com.example.homework23.data

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}

suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Resource<T> {
    val response = apiCall.invoke()
    try {
        if (response.isSuccessful) {
            return response.body()?.let {
                Resource.Success(data = it)
            } ?: Resource.Error(message = "some error")

        } else {
            return Resource.Error(message = response.message())
        }

    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                Resource.Error(message = throwable.message ?: "")
            }

            is HttpException -> {
                Resource.Error(message = throwable.message ?: "")
            }

            is IllegalStateException -> {
                Resource.Error(message = throwable.message ?: "")
            }

            else -> {}
        }
    }
    return Resource.Error(message = "error")
}