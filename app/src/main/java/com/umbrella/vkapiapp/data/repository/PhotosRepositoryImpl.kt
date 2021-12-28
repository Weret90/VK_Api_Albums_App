package com.umbrella.vkapiapp.data.repository

import com.umbrella.vkapiapp.data.mapper.toDomainModel
import com.umbrella.vkapiapp.data.remote.RetroService
import com.umbrella.vkapiapp.domain.entity.Album
import com.umbrella.vkapiapp.domain.entity.Photo
import com.umbrella.vkapiapp.domain.repository.PhotosRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PhotosRepositoryImpl(private val api: RetroService) : PhotosRepository {

    override fun getAlbums(): Single<List<Album>> {
        return api.getAlbums()
            .map { response ->
                response.data.albums.map {
                    it.toDomainModel()
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPhotosByAlbumId(albumId: String, offset: String, count: String): Single<List<Photo>> {
        return api.getPhotos(albumId, offset, count)
            .map { response ->
                response.data.photos.map {
                    it.toDomainModel()
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}