package com.shahinfasihi.githubsearch.presentation.github_user_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahinfasihi.githubsearch.util.Resource
import com.shahinfasihi.githubsearch.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val repository: GithubRepository) :
    ViewModel() {

    var state by mutableStateOf(UserListState())

    private var searchJob: Job? = null

    fun onEvent(event: UserListEvent) {
        when (event) {
            is UserListEvent.Refresh -> {
                getUserList()
            }
            is UserListEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query, isLoading = true)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    //to avoid sending multiple requests
                    delay(500)
                    getUserList()
                }
            }
        }
    }

    private fun getUserList(query: String = state.searchQuery.lowercase()) {
        viewModelScope.launch {
            repository.getUserList(query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { userList ->
                            state = state.copy(users = userList)
                        }
                    }
                    is Resource.Error -> {
                        state = state.copy(error = result.message)
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }


}