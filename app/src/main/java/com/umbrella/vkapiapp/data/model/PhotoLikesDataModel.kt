package com.umbrella.vkapiapp.data.model

import com.google.gson.annotations.SerializedName

data class PhotoLikesDataModel(
    @SerializedName("count")
    val count: Int
)