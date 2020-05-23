package com.tolo.app.data.usecase

import com.tolo.app.data.model.GithubPullRequest
import com.tolo.app.data.repository.PullRequestRepository

class GetPullRequests(
    private val githubPullRequestRepository: PullRequestRepository
) {

    suspend fun getPullRequests(owner: String, repo: String): List<GithubPullRequest> {
        return githubPullRequestRepository.getPullRequest(owner, repo)
    }

}