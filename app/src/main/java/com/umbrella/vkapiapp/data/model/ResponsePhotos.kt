package com.umbrella.vkapiapp.data.model

import com.google.gson.annotations.SerializedName

data class ResponsePhotos(
    @SerializedName("response")
    val data: PhotosDataModel
)


