package com.shahinfasihi.githubsearch.domain.repository

import com.shahinfasihi.githubsearch.domain.model.User
import com.shahinfasihi.githubsearch.domain.model.UserList
import com.shahinfasihi.githubsearch.util.Resource
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    suspend fun getUserList(
        query: String
    ): Flow<Resource<UserList>>

    fun getUser(
        username: String
    ): Flow<Resource<User>>
}