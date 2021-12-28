package com.umbrella.vkapiapp.domain.repository

import com.umbrella.vkapiapp.domain.entity.Album
import com.umbrella.vkapiapp.domain.entity.Photo
import io.reactivex.rxjava3.core.Single

interface PhotosRepository {

    fun getAlbums(): Single<List<Album>>
    fun getPhotosByAlbumId(albumId: String, offset: String, count: String): Single<List<Photo>>
}