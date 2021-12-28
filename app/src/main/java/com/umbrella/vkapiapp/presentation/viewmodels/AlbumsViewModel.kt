package com.umbrella.vkapiapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umbrella.vkapiapp.domain.entity.Album
import com.umbrella.vkapiapp.domain.usecase.ClearTokenUseCase
import com.umbrella.vkapiapp.domain.usecase.GetAlbumsUseCase
import com.umbrella.vkapiapp.presentation.utils.State

class AlbumsViewModel(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) : ViewModel() {

    private val _albumsLiveData = MutableLiveData<State<List<Album>>?>()
    val albumsLiveData: LiveData<State<List<Album>>?> = _albumsLiveData

    fun getAlbums() {
        _albumsLiveData.value = State.Loading
        getAlbumsUseCase().subscribe(
            {
                _albumsLiveData.value = State.Success(it)
            },
            {
                _albumsLiveData.value = State.Error(it)
            }
        )
    }

    fun clearToken() {
        clearTokenUseCase()
    }

    fun clearAlbumsLiveData() {
        _albumsLiveData.value = null
    }
}