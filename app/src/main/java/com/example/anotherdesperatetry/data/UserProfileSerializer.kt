package com.example.anotherdesperatetry.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.codelab.android.datastore.UserProfile
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream



private const val DATA_STORE_SILE_NAME = "user_profile.pb"

val Context.userProfileDataStore :DataStore<UserProfile> by dataStore(
    fileName = DATA_STORE_SILE_NAME,
    serializer = UserProfileSerializer
)


object UserProfileSerializer : Serializer<UserProfile> {
    override val defaultValue: UserProfile
        get() = UserProfile.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserProfile {
        try {
            return UserProfile.parseFrom(input)
        } catch (exception : InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserProfile, output: OutputStream) {
        t.writeTo(output)
    }
}