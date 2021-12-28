package com.umbrella.vkapiapp.data.remote

import com.umbrella.vkapiapp.data.model.ResponseAlbums
import com.umbrella.vkapiapp.data.model.ResponsePhotos
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("photos.getAlbums?need_system=1&need_covers=1")
    fun getAlbums(): Single<ResponseAlbums>

    @GET("photos.get?rev=0&extended=1")
    fun getPhotos(
        @Query("album_id") albumId: String,
        @Query("offset") offset: String,
        @Query("count") count: String,
    ): Single<ResponsePhotos>
}