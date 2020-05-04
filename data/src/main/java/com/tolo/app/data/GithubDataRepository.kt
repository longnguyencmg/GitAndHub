package com.tolo.app.data

import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.repository.GithubRepository
import com.tolo.app.data.source.GithubDataStoreFactory
import io.reactivex.Completable
import io.reactivex.Flowable

open class GithubDataRepository(private val factory: GithubDataStoreFactory) : GithubRepository {

    override fun clearRepos(): Completable {
        return factory.retrieveCacheDataStore().clearRepos()
    }

    override fun saveRepos(repos: List<GithubRepo>): Completable {
        return factory.retrieveCacheDataStore().saveRepos(repos)
    }

    override fun getRepos(): Flowable<List<GithubRepo>> {
        return factory.retrieveCacheDataStore().isCached()
            .flatMapPublisher { isCached ->
                factory.retrieveDataStore(isCached).getRepos()
            }
            .flatMap { repos ->
                saveRepos(repos).toSingle { repos }.toFlowable()
            }
    }

    override fun saveRepo(repo: GithubRepo): Completable {
        return factory.retrieveCacheDataStore().saveRepo(repo)
    }

    override fun getFavouriteRepos(): Flowable<List<GithubRepo>> {
        return factory.retrieveCacheDataStore().getFavouriteRepos()
    }

}