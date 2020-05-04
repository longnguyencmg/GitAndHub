package com.tolo.app.cache.dao


import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.tolo.app.cache.db.constants.GithubConstants
import com.tolo.app.cache.model.CachedGithubRepo

@Dao
abstract class CachedGithubRepoDao {

    @Query(GithubConstants.QUERY_REPOS)
    abstract fun getRepos(): List<CachedGithubRepo>

    @Query(GithubConstants.DELETE_ALL_REPOS)
    abstract fun clearRepos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepo(cachedRepo: CachedGithubRepo)

    @Query(GithubConstants.QUERY_FAVOURITES)
    abstract fun getFavouriteRepos(liked: Boolean): List<CachedGithubRepo>

}