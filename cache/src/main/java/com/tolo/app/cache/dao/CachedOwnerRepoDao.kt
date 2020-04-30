package com.tolo.app.cache.dao


import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tolo.app.cache.db.constants.OwnerConstants
import com.tolo.app.cache.model.CachedGithubOwner

@Dao
abstract class CachedOwnerRepoDao {

    @Query(OwnerConstants.QUERY_OWNER)
    abstract fun getOwner(repo_id: Int): CachedGithubOwner

    @Query(OwnerConstants.DELETE_ALL_OWNER)
    abstract fun clearOwners()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOwner(cachedRepo: CachedGithubOwner)

}