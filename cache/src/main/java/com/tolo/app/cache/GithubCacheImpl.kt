package com.tolo.app.cache

import android.util.Log
import com.tolo.app.cache.db.GithubReposDatabase
import com.tolo.app.cache.mapper.OwnerEntityMapper
import com.tolo.app.cache.mapper.RepoEntityMapper
import com.tolo.app.cache.model.CachedGithubRepo
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
            if (it.owner != null) {
                githubReposDatabase.cachedOwnerRepoDao()
                    .insertOwner(entityOwnerMapper.mapToCached(it.owner!!))
            }
        }
    }

    override suspend fun getRepos(): List<GithubRepo> {
        var list = githubReposDatabase.cachedGithubRepoDao().getRepositories()
        list = list.sortedByDescending { it.stargazersCount }
        return handleDataMapping(list)
    }

    private suspend fun handleDataMapping(list: List<CachedGithubRepo>): List<GithubRepo> {
        val result = mutableListOf<GithubRepo>()
        list.forEach { cachedItem ->
            val dataRepo = entityMapper.mapFromCached(cachedItem)
            Log.e("Github Cache Impl", "from cachedId - ${cachedItem.id}")
            dataRepo.owner = entityOwnerMapper.mapFromCached(
                githubReposDatabase.cachedOwnerRepoDao().getOwner(cachedItem.id)
            )
            result.add(dataRepo)
            Log.e(
                "Github Cache Impl",
                "Github Cache Impl - Add repo - ${dataRepo.owner?.login} from cachedId - ${cachedItem.id}"
            )
        }
        return result
    }

    override suspend fun isCached(): Boolean {
        return githubReposDatabase.cachedGithubRepoDao().getRepositories().isNotEmpty()
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
        val list = githubReposDatabase.cachedGithubRepoDao().getFavouriteRepositories(true)
        return handleDataMapping(list)
    }

    override suspend fun updateRepo(isFavourite: Boolean, id: Int) {
        githubReposDatabase.cachedGithubRepoDao().updateRepo(liked = isFavourite, repo_id = id)
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}