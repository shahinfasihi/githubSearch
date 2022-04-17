package com.shahinfasihi.githubsearch.domain.model

data class UserList(
    val totalCount: Int? = null,
    val incompleteResults: Boolean? = null,
    val userList: List<User>? = null
)
