package com.shahinfasihi.githubsearch.presentation.github_user_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahinfasihi.githubsearch.domain.model.User
import com.shahinfasihi.githubsearch.domain.repository.GithubRepository
import com.shahinfasihi.githubsearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: GithubRepository
) : ViewModel() {

    //    var state by mutableStateOf(UserState())
    val state: MutableLiveData<UserState> = MutableLiveData(UserState())

    init {
        savedStateHandle.get<String>(UserDetailFragment.USERNAME)?.let { username ->
            getUser(username)
        }
    }

    private fun getUser(username: String) {
        state.value?.let { state ->
            repository.getUser(username).onEach { result ->
//                this.state.value = state.copy(isLoading = true)
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { user ->
                            this.state.value = state.copy(
                                user = user,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        this.state.value = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Error -> {
                        this.state.value = state.copy(error = result.message, isLoading = false)
                    }
                }

            }.launchIn(viewModelScope)

        }
    }

    /**

    private fun loadData() {
    viewModelScope.launch {
    val username =
    savedStateHandle.get<String>(UserDetailFragment.USERNAME) ?: return@launch
    val userDetail = async { repository.getUser(username) }
    when (val result = userDetail.await()) {
    val userState = UserState(user = result.data as User?)
    state.postValue(userState)
    is Resource.Success -> {
    val userateSt = UserState(user = result.data as User?)
    state.postValue(userState)
    }
    is Resource.Error -> {

    }
    else -> Unit
    }

    }
    }


    init {
    viewModelScope.launch {
    val username =
    savedStateHandle.get<String>(UserDetailFragment.USERNAME) ?: return@launch
    state = state.copy(isLoading = true)
    repository.getUser(username).collect { result ->
    when (result) {
    is Resource.Success -> {
    result.data?.let { _user ->
    state = state.copy(user = _user, isLoading = false)
    }
    }
    is Resource.Error -> {
    state = state.copy(error = result.message, isLoading = false)
    }
    is Resource.Loading -> {
    state = state.copy(isLoading = result.isLoading)
    }
    }
    }
    }
    }


     **/

}