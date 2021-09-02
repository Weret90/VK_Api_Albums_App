package com.umbrella.vkapiapp.model.network

import com.umbrella.vkapiapp.model.AlbumsDTO
import com.umbrella.vkapiapp.model.PhotoDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("photos.getAlbums?need_system=1&need_covers=1&v=5.131")
    suspend fun getAlbums(
        @Query("access_token") token: String
    ): AlbumsDTO

    @GET("photos.get?rev=0&extended=1&v=5.131")
    suspend fun getPhotos(
        @Query("access_token") token: String,
        @Query("album_id") albumId: String,
        @Query("offset") offset: String
    ): PhotoDTO
}