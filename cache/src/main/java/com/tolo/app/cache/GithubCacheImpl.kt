package com.tolo.app.cache

import com.tolo.app.cache.db.GithubReposDatabase
import com.tolo.app.cache.mapper.OwnerEntityMapper
import com.tolo.app.cache.mapper.RepoEntityMapper
import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.source.GithubDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


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

    override fun clearRepos(): Completable {
        return Completable.defer {
            githubReposDatabase.cachedGithubRepoDao().clearRepos()
            Completable.complete()
        }
    }

    override fun saveRepos(repos: List<GithubRepo>): Completable {
        return Completable.defer {
            repos.forEach {
                //save both repos and owner
                githubReposDatabase.cachedGithubRepoDao().insertRepo(entityMapper.mapToCached(it))
                githubReposDatabase.cachedOwnerRepoDao()
                    .insertOwner(entityOwnerMapper.mapToCached(it.owner!!))
            }
            Completable.complete()
        }
    }

    override fun getRepos(): Flowable<List<GithubRepo>> {
        return Flowable.defer {
            Flowable.just(githubReposDatabase.cachedGithubRepoDao().getRepos())
        }.map {
            it.map { cachedItem ->
                val cachedRepo = entityMapper.mapFromCached(cachedItem)
                cachedRepo.owner = entityOwnerMapper.mapFromCached(
                    githubReposDatabase.cachedOwnerRepoDao().getOwner(cachedItem.id)
                )
                cachedRepo
            }
        }
    }

    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(githubReposDatabase.cachedGithubRepoDao().getRepos().isNotEmpty())
        }
    }

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}