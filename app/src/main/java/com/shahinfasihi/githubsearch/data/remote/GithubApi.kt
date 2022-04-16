package com.shahinfasihi.githubsearch.data.remote

import com.shahinfasihi.githubsearch.data.remote.dto.UserDto
import com.shahinfasihi.githubsearch.data.remote.dto.UserListDto
import retrofit2.http.*

interface GithubApi {

    //TODO : i should add pagination as a param to control the list size
    @GET("search/users")
    suspend fun searchUsers(
//        @Header("Authorization") authorization: String = TOKEN,
        @Query("q") query: String

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