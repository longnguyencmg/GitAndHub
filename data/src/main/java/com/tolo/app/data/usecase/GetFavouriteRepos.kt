package com.tolo.app.data.usecase

import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.repository.GithubRepository

open class GetFavouriteRepos(
    private val githubRepository: GithubRepository
) {

    suspend fun getFavouriteRepos(): List<GithubRepo> {
        return githubRepository.getFavouriteRepos()
    }
}