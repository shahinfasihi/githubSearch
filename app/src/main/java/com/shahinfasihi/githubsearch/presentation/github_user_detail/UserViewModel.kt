package com.shahinfasihi.githubsearch.presentation.github_user_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahinfasihi.githubsearch.domain.repository.GithubRepository
import com.shahinfasihi.githubsearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: GithubRepository
) : ViewModel() {

    val state: MutableLiveData<UserState> = MutableLiveData(UserState())

    init {
        savedStateHandle.get<String>(UserDetailFragment.USERNAME)?.let { username ->
            getUser(username)
        }
    }

    private fun getUser(username: String) {
        state.value?.let { state ->
            repository.getUser(username).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { user ->
                            this.state.value = state.copy(
                                user = user,
                                isLoading = false
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