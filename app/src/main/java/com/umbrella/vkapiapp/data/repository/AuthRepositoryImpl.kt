package com.umbrella.vkapiapp.data.repository

import com.umbrella.vkapiapp.data.local.LocalStorage
import com.umbrella.vkapiapp.domain.repository.AuthRepository

class AuthRepositoryImpl(private val localStorage: LocalStorage) : AuthRepository {

    override fun saveToken(token: String) {
        localStorage.saveToken(token)
    }

    override fun getToken(): String? {
        return localStorage.getToken()
    }

    override fun clearToken() {
        localStorage.clearToken()
    }
}