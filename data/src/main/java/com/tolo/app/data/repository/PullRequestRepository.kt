package com.tolo.app.data.repository

import com.tolo.app.data.model.GithubPullRequest
import io.reactivex.Flowable

interface PullRequestRepository {

    fun getPullRequest(owner: String, repo: String): Flowable<List<GithubPullRequest>>

}