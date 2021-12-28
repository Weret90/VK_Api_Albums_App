package com.umbrella.vkapiapp.data.local

import android.content.Context

class LocalStorageImpl(context: Context) : LocalStorage {

    companion object {
        private const val SHARED_PREF_NAME = "authorization"
        private const val KEY_TOKEN = "token"
    }

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    override fun clearToken() {
        sharedPreferences.edit().putString(KEY_TOKEN, null).apply()
    }
}