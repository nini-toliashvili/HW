package com.example.homework23.data.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.homework23.UserPreferences
import com.example.homework23.data.remote.api.ApiService
import com.example.homework23.data.remote.api.ApiServiceForPaging
import com.example.homework23.data.repository.RegisterRepositoryImpl
import com.example.homework23.data.repository.SessionRepositoryImpl
import com.example.homework23.data.locale.room.AppDatabase
import com.example.homework23.data.repository.AuthRepositoryImpl
import com.example.homework23.data.repository.RoomRepositoryImpl
import com.example.homework23.domain.repository.AuthRepository
import com.example.homework23.domain.repository.RegisterRepository
import com.example.homework23.domain.repository.RoomRepository
import com.example.homework23.domain.repository.SessionRepository
import com.example.homework23.domain.usecase.GetUsersUseCase
import com.example.homework23.domain.usecase.auth.LoginUseCase
import com.example.homework23.domain.usecase.register.RegisterUseCase
import com.example.homework23.domain.usecase.session.GetAuthTokenUseCase
import com.example.homework23.domain.usecase.session.IsUserLoggedInUseCase
import com.example.homework23.domain.usecase.session.LogoutUseCase
import com.example.homework23.domain.usecase.session.SaveSessionUseCase
import com.example.homework23.domain.usecase.session.SaveUserSessionUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor  {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)}.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return httpLoggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(logging: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    }

    @AuthRetrofit
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient)  : Retrofit {
       return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
           .client(okHttpClient)
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
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
    }


    @Provides
    fun provideGetUsersUseCase(roomRepository: RoomRepository) : GetUsersUseCase {
        return GetUsersUseCase(roomRepository)
    }

//    @Provides
//    fun provideRoomRepository(apiServiceForPaging: ApiServiceForPaging, database: AppDatabase) : RoomRepository {
//        return RoomRepositoryImpl(apiServiceForPaging, database)
//    }





    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    fun provideRegisterUseCase(registerRepository: RegisterRepository): RegisterUseCase {
        return RegisterUseCase(registerRepository)
    }

    //    @Provides
    //
    //
    //
    //    @Provides
    //    fun provideSessionRepository(sessionRepositoryImpl: SessionRepositoryImpl): SessionRepository {
    //        return sessionRepositoryImpl
    //    }

    @Provides
    fun provideSaveUserSessionUseCase(sessionRepository: SessionRepository): SaveUserSessionUseCase {
        return SaveUserSessionUseCase(sessionRepository)
    }

    @Provides
    fun provideGetAuthTokenUseCase(sessionRepository: SessionRepository): GetAuthTokenUseCase {
        return GetAuthTokenUseCase(sessionRepository)
    }

    @Provides
    fun provideIsUserLoggedInUseCase(sessionRepository: SessionRepository): IsUserLoggedInUseCase {
        return IsUserLoggedInUseCase(sessionRepository)
    }

    @Provides
    fun provideLogoutUseCase(sessionRepository: SessionRepository): LogoutUseCase {
        return LogoutUseCase(sessionRepository)
    }

    @Provides
    fun provideSaveSessionUseCase(sessionRepository: SessionRepository): SaveSessionUseCase {
        return SaveSessionUseCase(sessionRepository)
    }


}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRoomRepository(
        roomRepositoryImpl: RoomRepositoryImpl
    ) : RoomRepository

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository

    @Binds
    abstract fun bindSessionRepository(
        sessionRepositoryImpl: SessionRepositoryImpl
    ): SessionRepository
}
