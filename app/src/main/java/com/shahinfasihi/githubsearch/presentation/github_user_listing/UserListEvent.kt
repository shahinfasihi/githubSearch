package com.shahinfasihi.githubsearch.presentation.github_user_listing

import com.shahinfasihi.githubsearch.domain.model.UserList

sealed class UserListEvent {
    object Refresh : UserListEvent()
    object NextPage : UserListEvent()
    data class OnSearchQueryChange(val query: String) : UserListEvent()
    //click on screen can not count as as event because we can directly navigate to new screen
}
