package com.shahinfasihi.githubsearch.data.repository

import com.shahinfasihi.githubsearch.data.mapper.toUser
import com.shahinfasihi.githubsearch.data.mapper.toUserList
import com.shahinfasihi.githubsearch.data.remote.GithubApi
import com.shahinfasihi.githubsearch.domain.model.User
import com.shahinfasihi.githubsearch.domain.model.UserList
import com.shahinfasihi.githubsearch.domain.repository.GithubRepository
import com.shahinfasihi.githubsearch.domain.util.ErrorHandling
import com.shahinfasihi.githubsearch.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(private val api: GithubApi) : GithubRepository {

    override suspend fun getUserList(query: String): Flow<Resource<UserList>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val result = api.searchUsers(query)
                emit(Resource.Success(result.toUserList()))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = ErrorHandling.ERROR_LOADING_USER_LIST))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = ErrorHandling.ERROR_LOADING_USER_LIST))
            }

        }
    }

    override suspend fun getUser(username: String): Flow<Resource<User>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val result = api.getUser(username)
                emit(Resource.Success(result.toUser()))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = ErrorHandling.ERROR_LOADING_USER_DETAIL))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = ErrorHandling.ERROR_LOADING_USER_DETAIL))
            }
        }
    }


}