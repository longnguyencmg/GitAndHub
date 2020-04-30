package com.tolo.app.cache.mapper

import com.tolo.app.cache.model.CachedGithubOwner
import com.tolo.app.data.model.GithubOwner

class OwnerEntityMapper : EntityMapper<CachedGithubOwner, GithubOwner> {

    override fun mapFromCached(type: CachedGithubOwner): GithubOwner {
        return GithubOwner(
            type.login,
            type.id,
            type.avatarUrl,
            type.url,
            type.repo_id
        )
    }

    override fun mapToCached(type: GithubOwner): CachedGithubOwner {
        return CachedGithubOwner(
            type.login,
            type.id,
            type.avatarUrl,
            type.url,
            type.repo_id
        )
    }
}