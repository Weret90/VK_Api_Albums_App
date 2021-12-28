package com.umbrella.vkapiapp.domain.usecase

import com.umbrella.vkapiapp.domain.entity.Photo
import com.umbrella.vkapiapp.domain.repository.PhotosRepository
import io.reactivex.rxjava3.core.Single

class GetPhotosByAlbumIdUseCase(private val repository: PhotosRepository) {

    operator fun invoke(albumId: String, offset: String, count: String): Single<List<Photo>> {
        return repository.getPhotosByAlbumId(albumId, offset, count)
    }
}