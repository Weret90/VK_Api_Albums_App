package com.umbrella.vkapiapp.domain.usecase

import com.umbrella.vkapiapp.domain.entity.Album
import com.umbrella.vkapiapp.domain.repository.PhotosRepository
import io.reactivex.rxjava3.core.Single

class GetAlbumsUseCase(private val repository: PhotosRepository) {

    operator fun invoke(): Single<List<Album>> {
        return repository.getAlbums()
    }
}