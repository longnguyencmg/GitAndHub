package com.tolo.app.gitandhub.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.usecase.GetPullRequests
import com.tolo.app.data.usecase.UpdateRepoWithFavourite
import kotlinx.coroutines.launch

class DetailGithubRepoViewModel(
    private val getPullRequests: GetPullRequests,
    private val updateFavourites: UpdateRepoWithFavourite
) : ViewModel() {

    private val reposLiveData: MutableLiveData<DetailState> = MutableLiveData()
    private val updateFavorite: MutableLiveData<Boolean> = MutableLiveData()

    fun getPullRequests(): LiveData<DetailState> {
        return reposLiveData
    }

    fun getUpdateFavourite(): LiveData<Boolean> {
        return updateFavorite
    }

    fun fetchPullRequests(owner: String, repo: String) {
        reposLiveData.postValue(DetailState.Loading)
        viewModelScope.launch {
            try {
                val list = getPullRequests.getPullRequests(owner, repo)
                reposLiveData.postValue(DetailState.Success(list))
            } catch (exception: Exception) {
                reposLiveData.postValue(DetailState.Error(exception.message))
            }
        }
    }

    fun saveToFavourite(repo: GithubRepo) {
        viewModelScope.launch {
            try {
                updateFavourites.updateFavourite(repo)
                updateFavorite.postValue(true)
            } catch (exception: Exception) {
                updateFavorite.postValue(false)
            }
        }
    }
}