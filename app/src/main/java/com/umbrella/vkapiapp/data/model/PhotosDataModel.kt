package com.umbrella.vkapiapp.data.model

import com.google.gson.annotations.SerializedName

data class PhotosDataModel(
    @SerializedName("items")
    val photos: List<PhotoDataModel>
)