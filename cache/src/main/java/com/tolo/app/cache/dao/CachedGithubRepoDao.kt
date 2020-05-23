package com.tolo.app.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tolo.app.cache.db.constants.GithubConstants
import com.tolo.app.cache.model.CachedGithubRepo

@Dao
abstract class CachedGithubRepoDao {

    @Query(GithubConstants.QUERY_REPOS)
    abstract suspend fun getRepos(): List<CachedGithubRepo>

    @Query(GithubConstants.DELETE_ALL_REPOS)
    abstract suspend fun clearRepos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRepo(cachedRepo: CachedGithubRepo)

    @Query(GithubConstants.QUERY_FAVOURITES)
    abstract suspend fun getFavouriteRepos(liked: Boolean): List<CachedGithubRepo>

}