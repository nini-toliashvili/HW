package com.example.homework24.data.datamapper

import com.example.homework24.data.dto.OwnerDto
import com.example.homework24.data.dto.PostDto
import com.example.homework24.domain.entity.Owner
import com.example.homework24.domain.entity.Post

fun PostDto.toDomain(): Post {
    return Post(
        id = id,
        images = images,
        title = title,
        comments = comments,
        likes = likes,
        shareContent = share_content,
        owner = owner.toDomain()
    )
}

fun OwnerDto.toDomain(): Owner {
    return Owner(
        firstName = first_name,
        lastName = last_name,
        profile = profile,
        postDate = post_date
    )
}