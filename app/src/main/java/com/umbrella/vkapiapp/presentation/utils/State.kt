package com.umbrella.vkapiapp.presentation.utils

sealed class State<out T> {

    object Loading : State<Nothing>()
    data class Error(val error: Throwable) : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
}