package com.tolo.app.remote

import com.tolo.app.data.model.GithubPullRequest
import com.tolo.app.data.source.PullRequestDataStore
import com.tolo.app.remote.mapper.PullRequestEntityMapper
import io.reactivex.Flowable


class PullRequestRemoteImpl constructor(
    private val githubAPI: GithubAPI,
    private val entityMapper: PullRequestEntityMapper
) : PullRequestDataStore {

    override fun getPullRequest(owner: String, repo: String): Flowable<List<GithubPullRequest>> {
        return githubAPI.getPullRequests(owner, repo)
            .map {
                val entities = mutableListOf<GithubPullRequest>()
                it.forEach { responseItem -> entities.add(entityMapper.mapFromRemote(responseItem)) }
                entities
            }
    }
}