package com.tolo.app.data

import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.repository.GithubRepository
import com.tolo.app.data.source.GithubDataStoreFactory

open class GithubDataRepository(private val factory: GithubDataStoreFactory) : GithubRepository {

    override suspend fun clearRepos() {
        return factory.retrieveCacheDataStore().clearRepos()
    }

    override suspend fun saveRepos(repos: List<GithubRepo>) {
        factory.retrieveCacheDataStore().setLastCacheTime(System.currentTimeMillis())
        return factory.retrieveCacheDataStore().saveRepos(repos)
    }

    override suspend fun getRepos(): List<GithubRepo> {
        val isCached = factory.retrieveCacheDataStore().isCached()
        val list = factory.retrieveDataStore(isCached).getRepos()
        saveRepos(list)

        return list
    }

    override suspend fun saveRepo(repo: GithubRepo) {
        return factory.retrieveCacheDataStore().saveRepo(repo)
    }

    override suspend fun getFavouriteRepos(): List<GithubRepo> {
        return factory.retrieveCacheDataStore().getFavouriteRepos()
    }

    override suspend fun updateRepo(isFavourite: Boolean, id: Int) {
        return factory.retrieveCacheDataStore().updateRepo(isFavourite, id)
    }

}