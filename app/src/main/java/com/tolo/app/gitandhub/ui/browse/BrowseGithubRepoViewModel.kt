package com.tolo.app.gitandhub.ui.browse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolo.app.data.usecase.GetRepos
import kotlinx.coroutines.launch

class BrowseGithubRepoViewModel(private val getRepos: GetRepos) : ViewModel() {

    private val reposLiveData: MutableLiveData<BrowseState> = MutableLiveData()

    fun getRepos(): LiveData<BrowseState> {
        return reposLiveData
    }

    fun fetchRepos() {
        reposLiveData.postValue(BrowseState.Loading)
        viewModelScope.launch {
            try {
                val list = getRepos.getRepos()
                reposLiveData.postValue(BrowseState.Success(list))
            } catch (exception: Exception) {
                reposLiveData.postValue(BrowseState.Error(exception.message))
            }
        }
    }
}