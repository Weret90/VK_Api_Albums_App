package com.umbrella.vkapiapp.domain.entity

data class Photo(
    val date: Int,
    val id: Int,
    val sizes: List<PhotoSize>,
    val reposts: PhotoReposts,
    val likes: PhotoLikes,
    val comments: PhotoComments
)