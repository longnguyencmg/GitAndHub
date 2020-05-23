package com.tolo.app.data.source

import com.tolo.app.data.model.GithubPullRequest


interface PullRequestDataStore {

    suspend fun getPullRequest(owner: String, repo: String): List<GithubPullRequest>

}