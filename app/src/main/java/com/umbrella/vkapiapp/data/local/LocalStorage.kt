package com.umbrella.vkapiapp.data.local

interface LocalStorage {

    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}