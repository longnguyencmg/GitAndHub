package com.tolo.app.data.usecase

import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.repository.GithubRepository

open class GetRepos(
    private val githubRepository: GithubRepository
) {

    suspend fun getRepos(): List<GithubRepo> {
        return githubRepository.getRepos()
    }
}