package com.tolo.app.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.tolo.app.cache.db.constants.GithubConstants


@Entity(tableName = GithubConstants.TABLE_NAME)
data class CachedGithubRepo(
    @PrimaryKey
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
    val isFavourite: Boolean
)