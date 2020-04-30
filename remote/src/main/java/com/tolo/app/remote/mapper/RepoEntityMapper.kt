package com.tolo.app.remote.mapper

import com.tolo.app.data.model.GithubRepo
import com.tolo.app.remote.model.Repo

open class RepoEntityMapper : EntityMapper<Repo, GithubRepo> {

    override fun mapFromRemote(type: Repo): GithubRepo {
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
            OwnerEntityMapper(type.id).mapFromRemote(type.owner)
        )
    }
}