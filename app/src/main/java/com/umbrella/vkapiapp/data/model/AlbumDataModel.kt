package com.umbrella.vkapiapp.data.model

import com.google.gson.annotations.SerializedName

data class AlbumDataModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumb_src")
    val thumbSrc: String
)