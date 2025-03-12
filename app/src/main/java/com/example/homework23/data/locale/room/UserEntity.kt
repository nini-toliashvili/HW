package com.example.homework23.data.locale.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homework23.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
) {
    fun toDomainModel() = User(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}