package com.vanyookk.unipocket.data.remote

import android.content.Context
import com.vanyookk.unipocket.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenManager.getAccessToken(context = context)
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $token")
            .header("Accept", "application/json")
            .header("Accept-Language", "ru")
            .build()
        return chain.proceed(request)
    }
}
