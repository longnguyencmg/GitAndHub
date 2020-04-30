package com.tolo.app.remote.model

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("url") val url: String,
    var repo_id: Int
)