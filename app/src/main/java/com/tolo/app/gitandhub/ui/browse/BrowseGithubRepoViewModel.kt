package com.tolo.app.gitandhub.ui.browse

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tolo.app.data.usecase.GetRepos
import io.reactivex.disposables.Disposable

class BrowseGithubRepoViewModel(private val getRepos: GetRepos) : ViewModel() {

    private val reposLiveData: MutableLiveData<BrowseState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getRepos(): LiveData<BrowseState> {
        return reposLiveData
    }

    fun fetchRepos() {
        reposLiveData.postValue(BrowseState.Loading)
        disposable = getRepos.execute()
            .subscribe({
                reposLiveData.postValue(BrowseState.Success(it))
            }, {
                reposLiveData.postValue(BrowseState.Error(it.message))
            })
    }
}