package com.tolo.app.data.source


open class PullRequestDataStoreFactory(private val repoRemoteDataStore: PullRequestDataStore) {
    open fun retrieveRemoteDataStore(): PullRequestDataStore {
        return repoRemoteDataStore
    }
}