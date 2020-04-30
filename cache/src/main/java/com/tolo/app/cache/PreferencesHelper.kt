package com.tolo.app.cache

import android.content.Context
import android.content.SharedPreferences

open class PreferencesHelper(context: Context) {

    companion object {
        private const val PREF_BUFFER_PACKAGE_NAME = "com.tolo.app.gitandhub"
        private const val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val sharePreference: SharedPreferences

    init {
        sharePreference =
            context.getSharedPreferences(PREF_BUFFER_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    var lastCacheTime: Long
        get() = sharePreference.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = sharePreference.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()
}
