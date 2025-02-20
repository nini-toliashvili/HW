package com.example.homework23.data.di

import android.content.Context
import androidx.room.Room
import com.example.homework23.UserPreferences
import com.example.homework23.data.client.ApiService
import com.example.homework23.data.pagination.ApiServiceForPaging
import com.example.homework23.data.room.AppDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @AuthRetrofit
    @Provides
    fun provideRetrofit()  : Retrofit {
       return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @AuthRetrofit
    @Provides
    fun provideUserService(@AuthRetrofit retrofit : Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context) : UserPreferences {
        return UserPreferences(context)
    }


    @PagingRetrofit
    @Provides
    fun provideRetrofitForPaging() : Retrofit{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // Adding Kotlin adapter for Moshi
            .build()
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @PagingRetrofit
    @Provides
    fun providePagingService(@PagingRetrofit retrofit: Retrofit) : ApiServiceForPaging {
        return retrofit.create(ApiServiceForPaging::class.java)
    }



    @Provides
    fun provideDatabase(@ApplicationContext context: Context) :AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
    }
}