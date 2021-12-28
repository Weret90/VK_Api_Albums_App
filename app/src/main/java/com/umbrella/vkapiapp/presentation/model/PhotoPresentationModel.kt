package com.umbrella.vkapiapp.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoPresentationModel(
    val date: Int,
    val id: Int,
    val sizes: List<PhotoSizePresentationModel>,
    val reposts: PhotoRepostsPresentationModel,
    val likes: PhotoLikesPresentationModel,
    val comments: PhotoCommentsPresentationModel,
) : Parcelable