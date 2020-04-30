package com.tolo.app.remote.model

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("html_url") val htmlUrl: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("stargazers_count") val stargazersCount: Int?,
    @SerializedName("forks_count") val forksCount: Int?,
    @SerializedName("watchers_count") val watchersCount: Int?,
    @SerializedName("open_issues_count") val openIssuesCount: Int?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("owner") val owner: Owner
)