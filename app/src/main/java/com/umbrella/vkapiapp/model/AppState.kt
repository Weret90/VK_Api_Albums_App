package com.umbrella.vkapiapp.model

sealed class AppState {
    data class SuccessGetAlbums(val response: List<Album>) : AppState()
    data class SuccessGetPhotos(val response: PhotoList) : AppState()
    class Error(val throwable: Throwable) : AppState()
    object Loading : AppState()
}