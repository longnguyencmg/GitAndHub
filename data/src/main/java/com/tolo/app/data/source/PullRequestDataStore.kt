package com.tolo.app.data.source

import com.tolo.app.data.model.GithubPullRequest
import io.reactivex.Flowable


interface PullRequestDataStore {

    fun getPullRequest(owner: String, repo: String): Flowable<List<GithubPullRequest>>

}