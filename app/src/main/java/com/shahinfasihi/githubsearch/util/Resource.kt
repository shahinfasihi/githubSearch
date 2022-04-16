package com.shahinfasihi.githubsearch.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : Resource<T>(data)

    //message isn't null because we always have error message
    class Error<T>(data: T? = null, message: String) : Resource<T>(data, message)

    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}
