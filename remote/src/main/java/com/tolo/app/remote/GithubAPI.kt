package com.tolo.app.remote

import com.tolo.app.remote.model.PullRequestResponse
import com.tolo.app.remote.model.ReposResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @GET("/search/repositories?q=stars%3A>100&s=stars&type=Repositories&order=desc&per_page=20")
    fun getTopRepos(@Query("page") pageToFetch: Int): Flowable<ReposResponse>

    @GET("/repos/{owner}/{repo}/pulls")
    fun getPullRequests(@Path("owner") owner: String, @Path("repo") repo: String): Flowable<List<PullRequestResponse>>
}