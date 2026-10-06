package com.vanyookk.unipocket.data.remote

import android.content.Context
import com.vanyookk.unipocket.data.local.TokenManager
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class TokenAuthenticator(
    private val context: Context
) : Authenticator {
    private val baseUrl = "https://key.kbtu.kz/"
    private val clientId = "uninet-frontend"

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = TokenManager.getRefreshToken(context) ?: return null

        synchronized(this) {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val authApi = retrofit.create(AuthApi::class.java)

                val refreshResponse = authApi.refreshAccessTokenSync(
                    grantType = "refresh_token",
                    clientId = clientId,
                    refreshToken = refreshToken
                ).execute()

                if (refreshResponse.isSuccessful && refreshResponse.body() != null) {
                    val newTokens = refreshResponse.body()!!

                    TokenManager.saveTokens(
                        context = context,
                        accessToken = newTokens.accessToken,
                        refreshToken = newTokens.refreshToken
                    )

                    return response.request.newBuilder()
                        .header("Authorization", "Bearer ${newTokens.accessToken}")
                        .build()
                } else {
                    TokenManager.clear(context)
                    return null
                }
            } catch (e: IOException) {
                TokenManager.clear(context)
                return null
            }
        }
    }
}