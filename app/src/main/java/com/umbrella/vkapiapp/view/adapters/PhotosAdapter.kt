package com.umbrella.vkapiapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.umbrella.vkapiapp.databinding.ItemPhotoBinding
import com.umbrella.vkapiapp.model.Photo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosAdapterViewHolder>() {

    private var photos = ArrayList<Photo>()
    private var onPhotoClick: (Photo) -> Unit = {}

    fun addPhotos(photos: List<Photo>) {
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    fun getPhotos() = photos

    fun setOnPhotoClickListener(onPhotoClick: (Photo) -> Unit) {
        this.onPhotoClick = onPhotoClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosAdapterViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotosAdapterViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    private fun unixTimeConverter(unixTime: Int): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val date = Date(unixTime.toLong() * 1000)
        return sdf.format(date)
    }

    inner class PhotosAdapterViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            Picasso.get().load(photo.sizes[1].url).into(binding.photoCover)
            binding.photoTitle.text = photo.id.toString()
            binding.root.setOnClickListener {
                onPhotoClick(photo)
            }
            binding.dateOfLoad.text = unixTimeConverter(photo.date)
        }
    }
}