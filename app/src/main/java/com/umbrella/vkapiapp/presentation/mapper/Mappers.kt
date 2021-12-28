package com.umbrella.vkapiapp.presentation.mapper

import com.umbrella.vkapiapp.domain.entity.*
import com.umbrella.vkapiapp.presentation.model.*

fun Photo.toPresentationModel() = PhotoPresentationModel(
    date,
    id,
    sizes.map { it.toPresentationModel() },
    reposts.toPresentationModel(),
    likes.toPresentationModel(),
    comments.toPresentationModel()
)

private fun PhotoSize.toPresentationModel() = PhotoSizePresentationModel(url)

private fun PhotoReposts.toPresentationModel() = PhotoRepostsPresentationModel(count)

private fun PhotoLikes.toPresentationModel() = PhotoLikesPresentationModel(count)

private fun PhotoComments.toPresentationModel() = PhotoCommentsPresentationModel(count)