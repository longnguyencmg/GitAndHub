package com.tolo.app.data.model

import java.io.Serializable

data class GithubRepo(
    val id: Int,
    val name: String?,
    val fullName: String?,
    val htmlUrl: String?,
    val description: String?,
    val language: String?,
    val stargazersCount: Int?,
    val forksCount: Int?,
    val watchersCount: Int?,
    val openIssuesCount: Int?,
    val updatedAt: String?,
    var owner: GithubOwner?
) : Serializable