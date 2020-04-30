package com.tolo.app.remote

import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.source.GithubDataStore
import com.tolo.app.remote.mapper.RepoEntityMapper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


class GithubRemoteImpl constructor(
    private val githubAPI: GithubAPI,
    private val entityMapper: RepoEntityMapper
) : GithubDataStore {


    override fun getRepos(): Flowable<List<GithubRepo>> {
        return githubAPI.getTopRepos(1)
            .map {
                val entities = mutableListOf<GithubRepo>()
                it.items.forEach { repo -> entities.add(entityMapper.mapFromRemote(repo)) }
                entities
            }
    }

    override fun clearRepos(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveRepos(repos: List<GithubRepo>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCached(): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}