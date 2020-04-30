package com.tolo.app.remote.mapper

import com.tolo.app.data.model.GithubPullRequest
import com.tolo.app.remote.model.PullRequestResponse

open class PullRequestEntityMapper : EntityMapper<PullRequestResponse, GithubPullRequest> {

    override fun mapFromRemote(type: PullRequestResponse): GithubPullRequest {
        return GithubPullRequest(
            type.url,
            type.id,
            type.number,
            type.state,
            type.locked,
            type.title,
            type.body,
            type.updateAt,
            UserEntityMapper().mapFromRemote(type.user)
        )
    }
}