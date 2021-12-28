package com.umbrella.vkapiapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umbrella.vkapiapp.databinding.ItemPhotoBinding
import com.umbrella.vkapiapp.domain.entity.Photo

class PhotosAdapter : RecyclerView.Adapter<PhotosViewHolder>() {

    private var photos: MutableList<Photo> = mutableListOf()
    var onPhotoClickListener: ((Photo) -> Unit)? = null

    fun addPhotos(photos: List<Photo>) {
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    fun getPhotos(): List<Photo> {
        return photos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo)
        holder.itemView.setOnClickListener {
            onPhotoClickListener?.invoke(photo)
        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }
}

