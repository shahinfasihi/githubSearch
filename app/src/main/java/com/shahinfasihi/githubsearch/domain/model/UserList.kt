package com.shahinfasihi.githubsearch.domain.model

import com.shahinfasihi.githubsearch.data.remote.dto.UserDto

data class UserList(
    val totalCount: Int? = null,
    val incompleteResults: Boolean? = null,
    val userList: List<User>? = null
)
