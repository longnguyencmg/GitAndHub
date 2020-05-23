package com.tolo.app.gitandhub.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolo.app.data.usecase.GetFavouriteRepos
import kotlinx.coroutines.launch

class FavouriteViewModel(private val getFavouriteRepos: GetFavouriteRepos) : ViewModel() {

    private val reposLiveData: MutableLiveData<FavouriteState> = MutableLiveData()

    fun getRepos(): LiveData<FavouriteState> {
        return reposLiveData
    }

    fun fetchRepos() {
        reposLiveData.postValue(FavouriteState.Loading)
        viewModelScope.launch {
            try {
                val list = getFavouriteRepos.getFavouriteRepos()
                reposLiveData.postValue(FavouriteState.Success(list))
            } catch (exception: Exception) {
                reposLiveData.postValue(FavouriteState.Error(exception.message))
            }
        }
    }
}