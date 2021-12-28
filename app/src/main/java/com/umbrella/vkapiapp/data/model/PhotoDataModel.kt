package com.umbrella.vkapiapp.data.model

import com.google.gson.annotations.SerializedName

data class PhotoDataModel(
    @SerializedName("date")
    val date: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("sizes")
    val sizes: List<PhotoSizeDataModel>,
    @SerializedName("reposts")
    val reposts: PhotoRepostsDataModel,
    @SerializedName("likes")
    val likes: PhotoLikesDataModel,
    @SerializedName("comments")
    val comments: PhotoCommentsDataModel
)