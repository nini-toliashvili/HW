package com.example.homework25.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data class Loader(val isLoading: Boolean) : Resource<Nothing>()
}

fun <DTO, DOMAIN> Flow<Resource<DTO>>.mapResource(mapper: (DTO) -> DOMAIN): Flow<Resource<DOMAIN>> {
    return map { resource ->
        when (resource) {
            is Resource.Success -> {
                Resource.Success(data = mapper(resource.data))
            }
            is Resource.Error -> {
                Resource.Error(message = resource.message)
            }
            is Resource.Loader -> {
                Resource.Loader(isLoading = resource.isLoading)
            }
        }
    }
}