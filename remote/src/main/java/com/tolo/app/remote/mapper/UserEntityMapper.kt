package com.tolo.app.remote.mapper

import com.tolo.app.data.model.GithubPullRequestUser
import com.tolo.app.remote.model.PullRequestUser

class UserEntityMapper : EntityMapper<PullRequestUser, GithubPullRequestUser> {

    override fun mapFromRemote(type: PullRequestUser): GithubPullRequestUser {
        return GithubPullRequestUser(
            type.login,
            type.avatarUrl
        )
    }
}