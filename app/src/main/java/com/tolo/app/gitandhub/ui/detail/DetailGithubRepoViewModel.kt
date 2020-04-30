package com.tolo.app.gitandhub.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tolo.app.data.usecase.GetPullRequests
import io.reactivex.disposables.Disposable

class DetailGithubRepoViewModel(private val getPullRequests: GetPullRequests) : ViewModel() {

    private val reposLiveData: MutableLiveData<DetailState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getPullRequests(): LiveData<DetailState> {
        return reposLiveData
    }

    fun fetchPullRequests(owner: String, repo: String) {
        reposLiveData.postValue(DetailState.Loading)
        disposable = getPullRequests.execute(GetPullRequests.Params(owner, repo))
            .subscribe({
                reposLiveData.postValue(DetailState.Success(it))
            }, {
                reposLiveData.postValue(DetailState.Error(it.message))
            })
    }
}