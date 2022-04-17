package com.shahinfasihi.githubsearch.presentation.github_user_listing

sealed class UserListEvent {
    object Refresh : UserListEvent()
    object NextPage : UserListEvent()
    data class OnSearchQueryChange(val query: String) : UserListEvent()
    //On_click on don't count as as event because we can directly navigate to new screen - navigation_compose
}
