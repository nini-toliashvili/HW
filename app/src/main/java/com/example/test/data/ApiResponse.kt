package com.example.test.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class ApiResponse(
    val status: Boolean,
    val additional_data: String?,
    val options: String?,
    val permissions: List<String?>,
    val users: List<User>
)

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val avatar: String?,
    @ColumnInfo(name = "first_name") val first_name: String,
    @ColumnInfo(name = "last_name") val last_name: String,
    @ColumnInfo(name = "about") val about: String?,
    @ColumnInfo(name = "activation_status") val activation_status: Float

)