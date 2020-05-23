package com.tolo.app.remote

import com.tolo.app.data.model.GithubPullRequest
import com.tolo.app.data.source.PullRequestDataStore
import com.tolo.app.remote.mapper.PullRequestEntityMapper


class PullRequestRemoteImpl constructor(
    private val githubAPI: GithubAPI,
    private val entityMapper: PullRequestEntityMapper
) : PullRequestDataStore {

    override suspend fun getPullRequest(owner: String, repo: String): List<GithubPullRequest> {
        val response = githubAPI.getPullRequests(owner, repo)
        val entities = mutableListOf<GithubPullRequest>()
        response.forEach { responseItem -> entities.add(entityMapper.mapFromRemote(responseItem)) }
        return entities
    }
}