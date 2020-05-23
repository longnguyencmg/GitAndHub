package com.tolo.app.data

import com.tolo.app.data.model.GithubPullRequest
import com.tolo.app.data.repository.PullRequestRepository
import com.tolo.app.data.source.PullRequestDataStoreFactory

open class PullRequestDataRepository(private val factory: PullRequestDataStoreFactory) :
    PullRequestRepository {

    override suspend fun getPullRequest(owner: String, repo: String): List<GithubPullRequest> {
        return factory.retrieveRemoteDataStore().getPullRequest(owner, repo)
    }
}