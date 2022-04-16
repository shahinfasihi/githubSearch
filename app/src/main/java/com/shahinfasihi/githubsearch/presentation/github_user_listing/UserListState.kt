package com.shahinfasihi.githubsearch.presentation.github_user_listing

import com.shahinfasihi.githubsearch.domain.model.UserList

data class UserListState(
    val users: UserList? = null,
    val isLoading: Boolean? = false,
    val isRefreshing: Boolean? = false,
    val searchQuery: String = "",
    val error: String? = ""
)
