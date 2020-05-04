package com.tolo.app.data.usecase

import com.tolo.app.data.executor.PostExecutionThread
import com.tolo.app.data.executor.ThreadExecutor
import com.tolo.app.data.interactor.CompletableUseCase
import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.repository.GithubRepository
import io.reactivex.Completable

open class UpdateRepoWithFavourite(
    private val githubRepository: GithubRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    CompletableUseCase<UpdateRepoWithFavourite.Params?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Completable {
        params!!.repo.isFavourite = true
        return githubRepository.saveRepo(params.repo)
    }

    data class Params(val repo: GithubRepo)
}