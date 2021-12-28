package com.umbrella.vkapiapp.domain.repository

interface AuthRepository {

    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}