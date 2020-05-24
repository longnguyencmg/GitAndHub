package com.tolo.app.remote

import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.source.GithubDataStore
import com.tolo.app.remote.mapper.RepoEntityMapper


class GithubRemoteImpl constructor(
    private val githubAPI: GithubAPI,
    private val entityMapper: RepoEntityMapper
) : GithubDataStore {


    override suspend fun getRepos(): List<GithubRepo> {
        val response = githubAPI.getTopRepos(1)
        val entities = mutableListOf<GithubRepo>()
        response.items.forEach { repo -> entities.add(entityMapper.mapFromRemote(repo)) }

        return entities
    }

    override suspend fun clearRepos() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveRepos(repos: List<GithubRepo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun isCached(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveRepo(repo: GithubRepo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getFavouriteRepos(): List<GithubRepo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateRepo(isFavourite: Boolean, id: Int) {
        TODO("Not yet implemented")
    }
}