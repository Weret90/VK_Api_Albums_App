package com.umbrella.vkapiapp.data.mapper

import com.umbrella.vkapiapp.data.model.*
import com.umbrella.vkapiapp.domain.entity.*

fun AlbumDataModel.toDomainModel() = Album(
    id,
    title,
    thumbSrc
)

fun PhotoDataModel.toDomainModel() = Photo(
    date,
    id,
    sizes.map { it.toDomainModel() },
    reposts.toDomainModel(),
    likes.toDomainModel(),
    comments.toDomainModel()
)

private fun PhotoSizeDataModel.toDomainModel() = PhotoSize(url)

private fun PhotoRepostsDataModel.toDomainModel() = PhotoReposts(count)

private fun PhotoLikesDataModel.toDomainModel() = PhotoLikes(count)

private fun PhotoCommentsDataModel.toDomainModel() = PhotoComments(count)

