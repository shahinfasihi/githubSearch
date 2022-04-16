package com.shahinfasihi.githubsearch.data.mapper

import com.shahinfasihi.githubsearch.data.remote.dto.UserDto
import com.shahinfasihi.githubsearch.data.remote.dto.UserListDto
import com.shahinfasihi.githubsearch.domain.model.User
import com.shahinfasihi.githubsearch.domain.model.UserList


fun UserListDto.toUserList(): UserList {
    return UserList(
        totalCount = totalCount ?: 0,
        incompleteResults = incompleteResults ?: false,
        userList = userList?.map { it.toUser() }
    )
}

fun UserDto.toUser(): User {
    return User(
        login = login ?: "",
        id = id ?: 0,
        nodeId = nodeId ?: "",
        avatarUrl = avatarUrl ?: "",
        gravatarId = gravatarId ?: "",
        url = url ?: "",
        htmlUrl = htmlUrl ?: "",
        followersUrl = followersUrl ?: "",
        followingUrl = followingUrl ?: "",
        gistsUrl = gistsUrl ?: "",
        starredUrl = starredUrl ?: "",
        subscriptionsUrl = subscriptionsUrl ?: "",
        organizationsUrl = organizationsUrl ?: "",
        reposUrl = reposUrl ?: "",
        eventsUrl = eventsUrl ?: "",
        receivedEventsUrl = receivedEventsUrl ?: "",
        type = type ?: "",
        siteAdmin = siteAdmin ?: false,
        name = name ?: "",
        company = company ?: "",
        blog = blog ?: "",
        location = location ?: "",
        email = email ?: "",
        hireable = hireable ?: "",
        bio = bio ?: "",
        twitterUsername = twitterUsername ?: "",
        publicRepos = publicRepos ?: 0,
        publicGists = publicGists ?: 0,
        followers = followers ?: 0,
        following = following ?: 0,
        createdAt = createdAt ?: "",
        updatedAt = updatedAt ?: "",
        privateGists = privateGists ?: 0,
        totalPrivateRepos = totalPrivateRepos ?: 0,
        ownedPrivateRepos = ownedPrivateRepos ?: 0,
        diskUsage = diskUsage ?: 0,
        collaborators = collaborators ?: 0,
        twoFactorAuthentication = twoFactorAuthentication ?: false
    )
}