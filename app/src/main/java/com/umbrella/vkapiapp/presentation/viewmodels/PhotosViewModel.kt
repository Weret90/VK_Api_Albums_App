package com.umbrella.vkapiapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umbrella.vkapiapp.domain.entity.Photo
import com.umbrella.vkapiapp.domain.usecase.GetPhotosByAlbumIdUseCase
import com.umbrella.vkapiapp.presentation.utils.State

class PhotosViewModel(
    private val getPhotosByAlbumIdUseCase: GetPhotosByAlbumIdUseCase,
) : ViewModel() {

    private val _photosLiveData = MutableLiveData<State<List<Photo>>?>()
    val photosLiveData: LiveData<State<List<Photo>>?> = _photosLiveData

    fun getPhotosFromAlbum(albumId: String, offset: Int, count: Int) {
        _photosLiveData.value = State.Loading
        getPhotosByAlbumIdUseCase(albumId, offset.toString(), count.toString()).subscribe(
            {
                _photosLiveData.value = State.Success(it)
            },
            {
                _photosLiveData.value = State.Error(it)
            }
        )
    }

    fun clearPhotosLiveData() {
        _photosLiveData.value = null
    }
}