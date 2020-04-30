package com.tolo.app.gitandhub.di

import android.arch.persistence.room.Room
import com.tolo.app.cache.GithubCacheImpl
import com.tolo.app.cache.PreferencesHelper
import com.tolo.app.cache.db.GithubReposDatabase
import com.tolo.app.data.GithubDataRepository
import com.tolo.app.data.PullRequestDataRepository
import com.tolo.app.data.executor.JobExecutor
import com.tolo.app.data.executor.PostExecutionThread
import com.tolo.app.data.executor.ThreadExecutor
import com.tolo.app.data.repository.GithubRepository
import com.tolo.app.data.repository.PullRequestRepository
import com.tolo.app.data.source.GithubDataStore
import com.tolo.app.data.source.GithubDataStoreFactory
import com.tolo.app.data.source.PullRequestDataStore
import com.tolo.app.data.source.PullRequestDataStoreFactory
import com.tolo.app.data.usecase.GetPullRequests
import com.tolo.app.data.usecase.GetRepos
import com.tolo.app.gitandhub.UiThread
import com.tolo.app.gitandhub.ui.browse.BrowseAdapter
import com.tolo.app.gitandhub.ui.browse.BrowseGithubRepoViewModel
import com.tolo.app.gitandhub.ui.detail.DetailAdapter
import com.tolo.app.gitandhub.ui.detail.DetailGithubRepoViewModel
import com.tolo.app.remote.GithubRemoteImpl
import com.tolo.app.remote.PullRequestRemoteImpl
import com.tolo.app.remote.ServiceFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val applicationModule = module(override = true) {

    single { PreferencesHelper(androidContext()) }
    factory { com.tolo.app.remote.mapper.RepoEntityMapper() }
    factory { com.tolo.app.remote.mapper.OwnerEntityMapper(get()) }
    factory { com.tolo.app.remote.mapper.PullRequestEntityMapper() }

    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }
    single {
        Room.databaseBuilder(
            androidContext(),
            GithubReposDatabase::class.java,
            "githubrepos.db"
        ).build()
    }
    factory { get<GithubReposDatabase>().cachedGithubRepoDao() }

    factory<GithubDataStore>("remote") { GithubRemoteImpl(get(), get()) }
    factory<GithubDataStore>("local") { GithubCacheImpl(get(), get(), get(), get()) }
    factory<PullRequestDataStore>("remote") { PullRequestRemoteImpl(get(), get()) }
    factory { GithubDataStoreFactory(get("local"), get("remote")) }
    factory { PullRequestDataStoreFactory(get("remote")) }

    factory { com.tolo.app.cache.mapper.RepoEntityMapper() }
    factory { com.tolo.app.cache.mapper.OwnerEntityMapper() }
    factory { ServiceFactory.makeGithubService() }

    factory<GithubRepository> { GithubDataRepository(get()) }
    factory<PullRequestRepository> { PullRequestDataRepository(get()) }
}

val uiModule = module(override = true) {
    factory { BrowseAdapter() }
    factory { GetRepos(get(), get(), get()) }
    viewModel { BrowseGithubRepoViewModel(get()) }
    factory { DetailAdapter() }
    factory { GetPullRequests(get(), get(), get()) }
    viewModel { DetailGithubRepoViewModel(get()) }
}