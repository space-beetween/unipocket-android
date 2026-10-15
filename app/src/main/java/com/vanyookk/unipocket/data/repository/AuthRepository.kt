package com.vanyookk.unipocket.data.repository

import android.content.Context
import com.vanyookk.unipocket.data.remote.AuthApi
import com.vanyookk.unipocket.data.remote.PkceUtil
import java.util.regex.Pattern
import androidx.core.net.toUri
import com.vanyookk.unipocket.data.local.TokenManager

class AuthRepository (
    private val authApi: AuthApi,
    private val context: Context
) {
    private val clientId = "uninet-frontend"
    private val redirectUri = "https://uninet.kbtu.kz/silent-callback"

    fun isUserLoggedIn(): Boolean {
        return !TokenManager.getAccessToken(context).isNullOrBlank()
    }

    suspend fun login(username: String, password: String): Result<Unit> {
        return try {
            val codeVerifier = PkceUtil.generateCodeVerifier()
            val codeChallenge = PkceUtil.generateCodeChallenge(codeVerifier)

            val authResponse = authApi.getAuthPage(
                clientId = clientId,
                redirectUri = redirectUri,
                codeChallenge = codeChallenge
            )

            val authPageUrl = authResponse.raw().request.url.toString()
            val htmlBody =
                authResponse.body()?.string() ?: return Result.failure(Exception("Empty"))
            val actionUrl =
                parseFormActionUrl(htmlBody) ?: return Result.failure(Exception("Wrong Form"))
            val loginResponse = authApi.submitLoginForm(actionUrl, authPageUrl, username, password, "")

            if (loginResponse.code() != 302){
                return Result.failure(Exception("Wrong credentials"))
            }

            val locationHeader =
                loginResponse.headers()["Location"] ?: return Result.failure(Exception("Empty"))
            val code =
                locationHeader.toUri().getQueryParameter("code") ?: return Result.failure(Exception("Empty"))

            val tokenResponse = authApi.exchangeCodeForTokens(
                redirectUri = redirectUri,
                code = code,
                codeVerifier = codeVerifier
            )

            TokenManager.saveTokens(
                context=context,
                accessToken = tokenResponse.accessToken,
                refreshToken = tokenResponse.refreshToken
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    private fun parseFormActionUrl(html: String): String? {
        val pattern = Pattern.compile("action=\"([^\"]+)\"")
        val matcher = pattern.matcher(html)
        return if (matcher.find()) {
            matcher.group(1)?.replace("&amp;", "&")
        } else {
            null
        }
    }
}