package com.shahinfasihi.githubsearch.data.remote.dto

import com.squareup.moshi.Json

data class UserListDto(
    @field:Json(name = "total_count") val totalCount: Int? = null,
    @field:Json(name = "incomplete_results") val incompleteResults: Boolean? = null,
    @field:Json(name = "items") val userList: List<UserDto>? = null
)
