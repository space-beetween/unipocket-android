package com.vanyookk.unipocket.data.remote

import com.vanyookk.unipocket.data.remote.dto.TokenResponseDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @GET("realms/uninet/protocol/openid-connect/auth")
    suspend fun getAuthPage(
        @Query("client_id") clientId: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("response_type") responseType: String = "code",
        @Query("scope") scope: String = "openid profile",
        @Query("code_challenge") codeChallenge: String,
        @Query("code_challenge_method") codeChallengeMethod: String = "S256"
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST
    suspend fun submitLoginForm(
        @Url actionUrl: String,
        @Header("Referer") referer: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("credentialId") credentialId: String = "",
    ): Response<Unit>

    @FormUrlEncoded
    @POST("realms/uninet/protocol/openid-connect/token")
    suspend fun exchangeCodeForTokens(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("client_id") clientId: String = "uninet-frontend",
        @Field("redirect_uri") redirectUri: String,
        @Field("code") code: String,
        @Field("code_verifier") codeVerifier: String
    ): TokenResponseDto

    @FormUrlEncoded
    @POST("realms/uninet/protocol/openid-connect/token")
    fun refreshAccessTokenSync(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String = "uninet-frontend",
        @Field("refresh_token") refreshToken: String,
        @Field("scope") scope: String = "openid profile email"
    ): retrofit2.Call<TokenResponseDto>
}