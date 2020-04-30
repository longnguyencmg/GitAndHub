package com.tolo.app.remote.model

import com.google.gson.annotations.SerializedName

data class PullRequestUser(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)