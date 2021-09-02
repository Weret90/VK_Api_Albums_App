package com.umbrella.vkapiapp.model

import com.google.gson.annotations.SerializedName

data class PhotoDTO(
    @SerializedName("response")
    val photoList: PhotoList
)

data class PhotoList(
    @SerializedName("count")
    val totalNumOfPhotosInAlbum: Int,
    @SerializedName("items")
    val photos: List<Photo>
)

data class Photo(
    @SerializedName("album_id")
    val albumId: Int,
    @SerializedName("date")
    val date: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("sizes")
    val sizes: List<Size>,
    @SerializedName("text")
    val text: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("reposts")
    val reposts: Reposts,
    @SerializedName("likes")
    val likes: Likes,
    @SerializedName("comments")
    val comments: Comments
)

data class Size(
    @SerializedName("height")
    val height: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)

data class Comments(
    @SerializedName("count")
    val count: Int
)

data class Likes(
    @SerializedName("count")
    val count: Int,
    @SerializedName("user_likes")
    val userLikes: Int
)

data class Reposts(
    @SerializedName("count")
    val count: Int
)


