package com.umbrella.vkapiapp.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.ItemPhotoBinding
import com.umbrella.vkapiapp.domain.entity.Photo
import com.umbrella.vkapiapp.presentation.utils.unixTimeConverter

class PhotosViewHolder(private val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val LOW_QUALITY_PHOTO_SIZE = 1
    }

    fun bind(photo: Photo) {
        Picasso.get()
            .load(photo.sizes[LOW_QUALITY_PHOTO_SIZE].url)
            .placeholder(R.drawable.placeholder)
            .into(binding.photoCover)

        binding.photoTitle.text = photo.id.toString()
        binding.dateOfLoad.text = unixTimeConverter(photo.date)
    }
}