package com.umbrella.vkapiapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umbrella.vkapiapp.domain.usecase.ClearTokenUseCase
import com.umbrella.vkapiapp.domain.usecase.GetTokenUseCase
import com.umbrella.vkapiapp.domain.usecase.SaveTokenUseCase

class AuthorizationViewModel(
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) : ViewModel() {

    private val _tokenLiveData = MutableLiveData<String?>()
    val tokenLiveData: LiveData<String?> = _tokenLiveData

    fun saveToken(token: String) {
        saveTokenUseCase(token)
    }

    fun getToken() {
        _tokenLiveData.value = getTokenUseCase()
    }

    fun clearToken() {
        clearTokenUseCase()
    }
}