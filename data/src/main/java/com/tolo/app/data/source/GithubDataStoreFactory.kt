package com.tolo.app.data.source


open class GithubDataStoreFactory(
    private val repoCacheDataStore: GithubDataStore,
    private val repoRemoteDataStore: GithubDataStore
) {

    open fun retrieveDataStore(isCached: Boolean): GithubDataStore {
        if (isCached && !repoCacheDataStore.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    open fun retrieveCacheDataStore(): GithubDataStore {
        return repoCacheDataStore
    }

    open fun retrieveRemoteDataStore(): GithubDataStore {
        return repoRemoteDataStore
    }
}