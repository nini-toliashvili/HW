package com.example.homework25.common


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object ApiHelper {
    suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Flow<Resource<T>> =
        flow {
            emit(Resource.Loader(isLoading = true))
            val response = apiCall.invoke()
            try {
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        Resource.Success(data = it)
                    } ?: Resource.Error(message = "Error"))
                } else {
                    emit(Resource.Error(message = response.message()))
                }
                emit(Resource.Loader(isLoading = false))
            } catch (throwable: Throwable) {
                val errorMessage = when (throwable) {
                    is IOException -> throwable.message ?: ""
                    is HttpException -> throwable.message ?: ""
                    is IllegalStateException -> throwable.message ?: ""
                    else -> throwable.message ?: ""
                }
                emit(Resource.Error(message = errorMessage))
            }
        }


}
