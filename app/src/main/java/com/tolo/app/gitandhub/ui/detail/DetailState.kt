package com.tolo.app.gitandhub.ui.detail

import com.tolo.app.data.model.GithubPullRequest
import com.tolo.app.gitandhub.model.ResourceState

sealed class DetailState(
    val resourceState: ResourceState,
    val data: List<GithubPullRequest>? = null,
    val errorMessage: String? = null
) {

    data class Success(private val pullRequests: List<GithubPullRequest>) :
        DetailState(ResourceState.SUCCESS, pullRequests)

    data class Error(private val message: String? = null) :
        DetailState(ResourceState.ERROR, errorMessage = message)

    object Loading : DetailState(ResourceState.LOADING)
}