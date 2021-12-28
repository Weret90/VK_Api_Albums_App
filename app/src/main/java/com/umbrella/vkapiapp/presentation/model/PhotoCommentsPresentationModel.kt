package com.umbrella.vkapiapp.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoCommentsPresentationModel(
    val count: Int
) : Parcelable