package com.tolo.app.data.usecase

import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.repository.GithubRepository

open class UpdateRepoWithFavourite(
    private val githubRepository: GithubRepository
) {
    suspend fun updateFavourite(repo: GithubRepo) {
        repo.isFavourite = true
        return githubRepository.updateRepo(true, repo.id)
    }
}