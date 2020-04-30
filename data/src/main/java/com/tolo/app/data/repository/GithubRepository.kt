package com.tolo.app.data.repository

import com.tolo.app.data.model.GithubRepo
import io.reactivex.Completable
import io.reactivex.Flowable

interface GithubRepository {

    fun clearRepos(): Completable

    fun saveRepos(repos: List<GithubRepo>): Completable

    fun getRepos(): Flowable<List<GithubRepo>>

}