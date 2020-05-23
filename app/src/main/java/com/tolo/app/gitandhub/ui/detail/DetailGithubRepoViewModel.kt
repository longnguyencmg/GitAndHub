package com.tolo.app.gitandhub.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.usecase.GetPullRequests
import com.tolo.app.data.usecase.UpdateRepoWithFavourite
import io.reactivex.disposables.Disposable

class DetailGithubRepoViewModel(
    private val getPullRequests: GetPullRequests,
    private val updateFavourites: UpdateRepoWithFavourite
) : ViewModel() {

    private val reposLiveData: MutableLiveData<DetailState> = MutableLiveData()
    private var disposable: Disposable? = null
    private val updateFavorite: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getPullRequests(): LiveData<DetailState> {
        return reposLiveData
    }

    fun getUpdateFavourite(): LiveData<Boolean> {
        return updateFavorite
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

    fun saveToFavourite(repo: GithubRepo) {
        disposable = updateFavourites.execute(UpdateRepoWithFavourite.Params(repo))
            .subscribe({
                updateFavorite.postValue(true)
            }, {
                updateFavorite.postValue(false)
            })
    }
}