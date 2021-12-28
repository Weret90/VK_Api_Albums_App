package com.umbrella.vkapiapp.data.model

import com.google.gson.annotations.SerializedName

data class ResponseAlbums(
    @SerializedName("response")
    val data: AlbumsDataModel
)

