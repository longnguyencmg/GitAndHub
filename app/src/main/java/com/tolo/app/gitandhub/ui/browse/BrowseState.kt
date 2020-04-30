package com.tolo.app.gitandhub.ui.browse

import com.tolo.app.data.model.GithubRepo
import com.tolo.app.gitandhub.model.ResourceState

sealed class BrowseState(
    val resourceState: ResourceState,
    val data: List<GithubRepo>? = null,
    val errorMessage: String? = null
) {

    data class Success(private val repos: List<GithubRepo>) :
        BrowseState(ResourceState.SUCCESS, repos)

    data class Error(private val message: String? = null) :
        BrowseState(ResourceState.ERROR, errorMessage = message)

    object Loading : BrowseState(ResourceState.LOADING)
}