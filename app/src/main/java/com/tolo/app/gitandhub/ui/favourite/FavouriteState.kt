package com.tolo.app.gitandhub.ui.favourite

import com.tolo.app.data.model.GithubRepo
import com.tolo.app.gitandhub.model.ResourceState

sealed class FavouriteState(
    val resourceState: ResourceState,
    val data: List<GithubRepo>? = null,
    val errorMessage: String? = null
) {

    data class Success(private val repos: List<GithubRepo>) :
        FavouriteState(ResourceState.SUCCESS, repos)

    data class Error(private val message: String? = null) :
        FavouriteState(ResourceState.ERROR, errorMessage = message)

    object Loading : FavouriteState(ResourceState.LOADING)
}