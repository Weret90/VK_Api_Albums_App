package com.umbrella.vkapiapp.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://api.vk.com/method/"

class RetroInstance {
    companion object {
        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}