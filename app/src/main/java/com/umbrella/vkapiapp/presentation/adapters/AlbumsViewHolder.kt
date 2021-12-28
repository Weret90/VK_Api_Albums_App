package com.umbrella.vkapiapp.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.ItemAlbumBinding
import com.umbrella.vkapiapp.domain.entity.Album

class AlbumsViewHolder(private val binding: ItemAlbumBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(album: Album) {
        Picasso.get()
            .load(album.thumbSrc)
            .placeholder(R.drawable.placeholder)
            .into(binding.albumCover)

        binding.albumTitle.text = album.title
    }
}