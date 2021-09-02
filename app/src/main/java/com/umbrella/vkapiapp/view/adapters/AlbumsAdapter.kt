package com.umbrella.vkapiapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.umbrella.vkapiapp.databinding.ItemAlbumBinding
import com.umbrella.vkapiapp.model.Album

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumsAdapterViewHolder>() {

    private var albums: List<Album> = ArrayList()
    private var onAlbumClick: (Album) -> Unit = {}

    fun setAlbums(albums: List<Album>) {
        this.albums = albums
        notifyDataSetChanged()
    }

    fun getAlbums() = albums

    fun setOnAlbumClickListener(onAlbumClick: (Album) -> Unit) {
        this.onAlbumClick = onAlbumClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsAdapterViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumsAdapterViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    inner class AlbumsAdapterViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            Picasso.get().load(album.thumbSrc).into(binding.albumCover)
            binding.albumTitle.text = album.title
            binding.root.setOnClickListener {
                onAlbumClick(album)
            }
        }
    }
}