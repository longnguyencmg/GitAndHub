package com.tolo.app.gitandhub.ui.favourite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tolo.app.data.usecase.GetFavouriteRepos
import io.reactivex.disposables.Disposable

class FavouriteViewModel(private val getFavouriteRepos: GetFavouriteRepos) : ViewModel() {

    private val reposLiveData: MutableLiveData<FavouriteState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getRepos(): LiveData<FavouriteState> {
        return reposLiveData
    }

    fun fetchRepos() {
        reposLiveData.postValue(FavouriteState.Loading)
        disposable = getFavouriteRepos.execute()
            .subscribe({
                reposLiveData.postValue(FavouriteState.Success(it))
            }, {
                reposLiveData.postValue(FavouriteState.Error(it.message))
            })
    }
}