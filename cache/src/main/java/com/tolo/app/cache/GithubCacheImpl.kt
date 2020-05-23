package com.tolo.app.cache

import com.tolo.app.cache.db.GithubReposDatabase
import com.tolo.app.cache.mapper.OwnerEntityMapper
import com.tolo.app.cache.mapper.RepoEntityMapper
import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.source.GithubDataStore


class GithubCacheImpl constructor(
    private val githubReposDatabase: GithubReposDatabase,
    private val entityMapper: RepoEntityMapper,
    private val entityOwnerMapper: OwnerEntityMapper,
    private val preferencesHelper: PreferencesHelper
) : GithubDataStore {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    internal fun getDatabase(): GithubReposDatabase {
        return githubReposDatabase
    }

    override suspend fun clearRepos() {
        githubReposDatabase.cachedGithubRepoDao().clearRepos()
    }

    override suspend fun saveRepos(repos: List<GithubRepo>) {
        repos.forEach {
            //save both repos and owner
            githubReposDatabase.cachedGithubRepoDao().insertRepo(entityMapper.mapToCached(it))
            githubReposDatabase.cachedOwnerRepoDao()
                .insertOwner(entityOwnerMapper.mapToCached(it.owner!!))
        }
    }

    override suspend fun getRepos(): List<GithubRepo> {
        val list = githubReposDatabase.cachedGithubRepoDao().getRepos()
        return list.map { cachedItem ->
            val cachedRepo = entityMapper.mapFromCached(cachedItem)
            cachedRepo.owner = entityOwnerMapper.mapFromCached(
                githubReposDatabase.cachedOwnerRepoDao().getOwner(cachedItem.id)
            )
            cachedRepo
        }
    }

    override suspend fun isCached(): Boolean {
        return githubReposDatabase.cachedGithubRepoDao().getRepos().isNotEmpty()
    }

    override suspend fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    override suspend fun saveRepo(repo: GithubRepo) {
        githubReposDatabase.cachedGithubRepoDao().insertRepo(entityMapper.mapToCached(repo))
        githubReposDatabase.cachedOwnerRepoDao()
            .insertOwner(entityOwnerMapper.mapToCached(repo.owner!!))
    }

    override suspend fun getFavouriteRepos(): List<GithubRepo> {
        val list = githubReposDatabase.cachedGithubRepoDao().getFavouriteRepos(true)
        return list.map { cachedItem ->
            val cachedRepo = entityMapper.mapFromCached(cachedItem)
            cachedRepo.owner = entityOwnerMapper.mapFromCached(
                githubReposDatabase.cachedOwnerRepoDao().getOwner(cachedItem.id)
            )
            cachedRepo
        }
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}