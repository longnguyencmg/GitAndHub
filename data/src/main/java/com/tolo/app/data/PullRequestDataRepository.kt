package com.tolo.app.data

import com.tolo.app.data.model.GithubPullRequest
import com.tolo.app.data.repository.PullRequestRepository
import com.tolo.app.data.source.PullRequestDataStoreFactory
import io.reactivex.Flowable

open class PullRequestDataRepository(private val factory: PullRequestDataStoreFactory) :
    PullRequestRepository {

    override fun getPullRequest(owner: String, repo: String): Flowable<List<GithubPullRequest>> {
        return factory.retrieveRemoteDataStore().getPullRequest(owner, repo)
    }
}