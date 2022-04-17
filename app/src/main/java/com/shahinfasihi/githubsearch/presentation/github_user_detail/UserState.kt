package com.shahinfasihi.githubsearch.presentation.github_user_detail

import com.shahinfasihi.githubsearch.domain.model.User

data class UserState(
    val user: User? = null,
    val isLoading: Boolean? = false,
    val error: String? = ""
)