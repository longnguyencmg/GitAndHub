package com.tolo.app.data.repository

import com.tolo.app.data.model.GithubPullRequest

interface PullRequestRepository {

    suspend fun getPullRequest(owner: String, repo: String): List<GithubPullRequest>

}