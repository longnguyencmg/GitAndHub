package com.tolo.app.data.model

data class GithubPullRequest(
    val url: String,
    val id: Int,
    val number: Int,
    val state: String,
    val locked: Boolean,
    val title: String,
    val body: String,
    val updateAt: String, //2011-01-26T19:01:12Z
    val user: GithubPullRequestUser
)