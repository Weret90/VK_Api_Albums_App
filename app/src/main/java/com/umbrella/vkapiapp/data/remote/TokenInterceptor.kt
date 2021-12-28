package com.umbrella.vkapiapp.data.remote

import com.umbrella.vkapiapp.domain.usecase.GetTokenUseCase
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val getTokenUseCase: GetTokenUseCase,
) : Interceptor {

    companion object {
        private const val QUERY_ACCESS_TOKEN = "access_token"
        private const val QUERY_VERSION_VK_API = "v"
        private const val VERSION_VK_API = "5.131"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val token = getTokenUseCase()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(QUERY_ACCESS_TOKEN, token)
            .addQueryParameter(QUERY_VERSION_VK_API, VERSION_VK_API)
            .build()
        val newRequest = request.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}