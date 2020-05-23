package com.tolo.app.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tolo.app.cache.dao.CachedGithubRepoDao
import com.tolo.app.cache.dao.CachedOwnerRepoDao
import com.tolo.app.cache.model.CachedGithubOwner
import com.tolo.app.cache.model.CachedGithubRepo

@Database(entities = [CachedGithubRepo::class, CachedGithubOwner::class], version = 3)
abstract class GithubReposDatabase : RoomDatabase() {

    abstract fun cachedGithubRepoDao(): CachedGithubRepoDao
    abstract fun cachedOwnerRepoDao(): CachedOwnerRepoDao

    private var INSTANCE: GithubReposDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): GithubReposDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubReposDatabase::class.java, "githubrepos.db"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }

}