package com.tolo.app.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tolo.app.cache.db.constants.OwnerConstants

@Entity(tableName = OwnerConstants.TABLE_NAME)
data class CachedGithubOwner(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatarUrl: String,
    val url: String,
    var repo_id: Int
)