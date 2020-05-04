package com.tolo.app.cache.mapper

import com.tolo.app.cache.model.CachedGithubRepo
import com.tolo.app.data.model.GithubRepo


open class RepoEntityMapper : EntityMapper<CachedGithubRepo, GithubRepo> {

    override fun mapToCached(type: GithubRepo): CachedGithubRepo {
        return CachedGithubRepo(
            type.id,
            type.name,
            type.fullName,
            type.htmlUrl,
            type.description,
            type.language,
            type.stargazersCount,
            type.forksCount,
            type.watchersCount,
            type.openIssuesCount,
            type.updatedAt,
            type.isFavourite
        )
    }


    override fun mapFromCached(type: CachedGithubRepo): GithubRepo {
        return GithubRepo(
            type.id,
            type.name,
            type.fullName,
            type.htmlUrl,
            type.description,
            type.language,
            type.stargazersCount,
            type.forksCount,
            type.watchersCount,
            type.openIssuesCount,
            type.updatedAt,
            null,
            type.isFavourite
        )
    }

}