package com.tolo.app.data.repository

import com.tolo.app.data.model.GithubRepo

interface GithubRepository {

    suspend fun clearRepos()

    suspend fun saveRepos(repos: List<GithubRepo>)

    suspend fun getRepos(): List<GithubRepo>

    suspend fun saveRepo(repo: GithubRepo)

    suspend fun getFavouriteRepos(): List<GithubRepo>

    suspend fun updateRepo(isFavourite: Boolean, id: Int)

}