package com.tolo.app.gitandhub

import android.app.Application
import com.tolo.app.gitandhub.di.applicationModule
import com.tolo.app.gitandhub.di.uiModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule, uiModule))
        setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

}
