package com.shahinfasihi.githubsearch.presentation.github_user_listing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahinfasihi.githubsearch.util.Resource
import com.shahinfasihi.githubsearch.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val repository: GithubRepository) :
    ViewModel() {

    val state: MutableLiveData<UserListState> = MutableLiveData(UserListState())

    private var searchJob: Job? = null

    init {
        state.value = state.value?.copy(searchQuery = "payConiq")
        getUserList()
    }

    fun onEvent(event: UserListEvent) {
        when (event) {
            is UserListEvent.Refresh -> {
                getUserList()
            }
            is UserListEvent.OnSearchQueryChange -> {
                state.value = state.value?.copy(searchQuery = event.query, isLoading = true)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    //to avoid sending multiple requests
                    delay(500)
                    newSearch()
                }
            }
            is UserListEvent.NextPage -> {
                nextPage()
            }
        }
    }

    private fun nextPage() {
        incrementPageNumber()
        getUserList()
    }

    private fun newSearch() {
        resetPage()
        clearList()
        getUserList()
    }

    private fun clearList() {
        state.value?.let { state ->
            this.state.value = state.copy(users = null)
        }
    }

    private fun resetPage() {
        state.value = state.value?.copy(page = 1)
    }

    private fun incrementPageNumber() {
        state.value?.let { state ->
            this.state.value = state.copy(page = state.page + 1)
        }
    }

    private fun getUserList() {
        state.value?.let { state ->
            repository.getUserList(state.searchQuery, state.page).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { userList ->
                            this.state.value = state.copy(
                                users = userList,
                                isLoading = false,
                                page = state.page
                            )
                        }
                    }
                    is Resource.Loading -> {
                        this.state.value = state.copy(isLoading = resource.isLoading)
                    }
                    is Resource.Error -> {
                        this.state.value = state.copy(error = resource.message, isLoading = false)
                    }
                }

            }.launchIn(viewModelScope)
        }

    }
}