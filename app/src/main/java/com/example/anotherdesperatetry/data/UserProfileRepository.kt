package com.example.anotherdesperatetry.data

import androidx.datastore.core.DataStore
import com.codelab.android.datastore.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfileRepository(private val dataStore: DataStore<UserProfile>) {

    suspend fun saveUserProfile(firstname:String, lastname:String, email:String) {
        dataStore.updateData { info ->
            info.toBuilder()
                .setFirstname(firstname)
                .setLastname(lastname)
                .setEmail(email)
                .build()
        }
    }

    val userProfileFlow : Flow<UserProfile> = dataStore.data
        .map { info ->
            info
        }
}