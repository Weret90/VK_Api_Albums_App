package com.umbrella.vkapiapp.data.model

import com.google.gson.annotations.SerializedName

data class AlbumsDataModel(
    @SerializedName("items")
    val albums: List<AlbumDataModel>
)