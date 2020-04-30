package com.tolo.app.data.source

import com.tolo.app.data.model.GithubRepo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface GithubDataStore {

    fun clearRepos(): Completable

    fun saveRepos(repos: List<GithubRepo>): Completable

    fun getRepos(): Flowable<List<GithubRepo>>

    fun isCached(): Single<Boolean>

    fun setLastCacheTime(lastCache: Long)

    fun isExpired(): Boolean

}