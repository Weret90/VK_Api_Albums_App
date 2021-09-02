package com.umbrella.vkapiapp.model


import com.google.gson.annotations.SerializedName

data class AlbumsDTO(
    @SerializedName("response")
    val albumsList: AlbumsList
)

data class AlbumsList(
    @SerializedName("items")
    val albums: List<Album>
)

data class Album(
    @SerializedName("created")
    val created: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("thumb_id")
    val thumbId: Int,
    @SerializedName("thumb_is_last")
    val thumbIsLast: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated")
    val updated: Int,
    @SerializedName("thumb_src")
    val thumbSrc: String
)

