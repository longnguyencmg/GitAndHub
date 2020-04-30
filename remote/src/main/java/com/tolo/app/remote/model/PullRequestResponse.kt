package com.tolo.app.remote.model

import com.google.gson.annotations.SerializedName

data class PullRequestResponse(
    @SerializedName("url") val url: String,
    @SerializedName("id") val id: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("state") val state: String,
    @SerializedName("locked") val locked: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("updated_at") val updateAt: String, //2011-01-26T19:01:12Z
    @SerializedName("user") val user: PullRequestUser
)