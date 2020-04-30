package com.tolo.app.data.model

import java.io.Serializable

data class GithubOwner(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val url: String,
    var repo_id: Int
) : Serializable