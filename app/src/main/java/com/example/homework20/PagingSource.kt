package com.example.homework20

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homework20.datamodel.User
import retrofit2.HttpException
import java.io.IOException

class PagingSource(val apiService: ApiService) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        try {

            val nextPageNumber = params.key ?: 1
            val response = apiService.getUsers(nextPageNumber, params.loadSize)
            return LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = if (nextPageNumber >= response.totalPages) null else nextPageNumber + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}