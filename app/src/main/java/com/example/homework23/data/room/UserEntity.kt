package com.example.homework23.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
)