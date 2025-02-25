package com.example.homework24.presentation.user.mapper

import com.example.homework24.domain.entity.Post
import com.example.homework24.presentation.user.model.PostUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Post.toUiModel(): PostUiModel {
    return PostUiModel(
        id = id,
        images = images ?: emptyList(),
        title = title,
        comments = "$comments comments",
        likes = "$likes likes",
        ownerFullName = formatFullName(owner.firstName, owner.lastName),
        ownerProfile = owner.profile,
        formattedDate = formatTimestamp(owner.postDate)
    )
}
private fun formatFullName(firstName: String, lastName: String): String {
    return if (lastName.isNotEmpty()) "$firstName $lastName" else firstName
}

private fun formatTimestamp(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd MMMM 'at' h:mm a", Locale.getDefault())
    return formatter.format(Date(timestamp * 1000))
}