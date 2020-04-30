package com.tolo.app.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.tolo.app.cache.dao.CachedGithubRepoDao
import com.tolo.app.cache.dao.CachedOwnerRepoDao
import com.tolo.app.cache.model.CachedGithubOwner
import com.tolo.app.cache.model.CachedGithubRepo

@Database(entities = [CachedGithubRepo::class, CachedGithubOwner::class], version = 2)
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