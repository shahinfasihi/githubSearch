package com.shahinfasihi.githubsearch.data.remote

import com.shahinfasihi.githubsearch.data.remote.dto.UserDto
import com.shahinfasihi.githubsearch.data.remote.dto.UserListDto
import retrofit2.http.*

interface GithubApi {

    @GET("search/users")
    suspend fun searchUsers(
//        @Header("Authorization") authorization: String = TOKEN,
        @Query("q") query: String,
        @Query("page") page: Int

    ): UserListDto

    @GET("users/{username}")
    suspend fun getUser(
//        @Header("Authorization") authorization: String = TOKEN,
        @Path("username") username: String
    ): UserDto

    companion object {
        // usually you don't need authorization when calling the api
        // the key just generated for testing more than 5 time with same IP_Address
        const val TOKEN = "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"
        const val BASE_URL = "https://api.github.com/"
    }
}