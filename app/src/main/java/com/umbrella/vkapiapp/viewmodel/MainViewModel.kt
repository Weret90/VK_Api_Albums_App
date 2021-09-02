package com.umbrella.vkapiapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umbrella.vkapiapp.model.AppState
import com.umbrella.vkapiapp.model.network.RetroInstance
import com.umbrella.vkapiapp.model.network.RetroService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val albumsLiveData = MutableLiveData<AppState>()
    val photosLiveData = MutableLiveData<AppState>()

    fun makeApiCallGetAlbums(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            albumsLiveData.postValue(AppState.Loading)
            try {
                val retroInstance =
                    RetroInstance.getRetroInstance().create(RetroService::class.java)
                val response = retroInstance.getAlbums(token)
                albumsLiveData.postValue(AppState.SuccessGetAlbums(response.albumsList.albums))
            } catch (e: Exception) {
                albumsLiveData.postValue(AppState.Error(e))
            }
        }
    }

    fun makeApiCallGetPhotos(token: String, albumId: String, offset: String) {
        viewModelScope.launch(Dispatchers.IO) {
            photosLiveData.postValue(AppState.Loading)
            try {
                val retroInstance =
                    RetroInstance.getRetroInstance().create(RetroService::class.java)
                val response = retroInstance.getPhotos(token, albumId, offset)
                photosLiveData.postValue(AppState.SuccessGetPhotos(response.photoList))
            } catch (e: Exception) {
                photosLiveData.postValue(AppState.Error(e))
            }
        }
    }
}