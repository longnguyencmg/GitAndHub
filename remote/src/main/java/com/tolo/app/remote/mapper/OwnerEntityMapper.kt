package com.tolo.app.remote.mapper

import com.tolo.app.data.model.GithubOwner
import com.tolo.app.remote.model.Owner

class OwnerEntityMapper(private val repo_id: Int) : EntityMapper<Owner, GithubOwner> {

    override fun mapFromRemote(type: Owner): GithubOwner {
        return GithubOwner(
            type.login,
            type.id,
            type.avatarUrl,
            type.url,
            repo_id
        )
    }
}