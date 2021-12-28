package com.umbrella.vkapiapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umbrella.vkapiapp.databinding.ItemAlbumBinding
import com.umbrella.vkapiapp.domain.entity.Album

class AlbumsAdapter : RecyclerView.Adapter<AlbumsViewHolder>() {

    private var albums: List<Album> = listOf()
    var onAlbumClickListener: ((Album) -> Unit)? = null

    fun setAlbums(albums: List<Album>) {
        this.albums = albums
        notifyDataSetChanged()
    }

    fun getAlbums(): List<Album> {
        return albums
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
        holder.itemView.setOnClickListener {
            onAlbumClickListener?.invoke(album)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}

