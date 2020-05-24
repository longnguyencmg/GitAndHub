package com.tolo.app.data.source

import com.tolo.app.data.model.GithubRepo


interface GithubDataStore {

    suspend fun clearRepos()

    suspend fun saveRepos(repos: List<GithubRepo>)

    suspend fun getRepos(): List<GithubRepo>

    suspend fun isCached(): Boolean

    suspend fun setLastCacheTime(lastCache: Long)

    fun isExpired(): Boolean

    suspend fun saveRepo(repo: GithubRepo)

    suspend fun getFavouriteRepos(): List<GithubRepo>

    suspend fun updateRepo(isFavourite: Boolean, id: Int)

}