package com.tolo.app.data.usecase

import com.tolo.app.data.executor.PostExecutionThread
import com.tolo.app.data.executor.ThreadExecutor
import com.tolo.app.data.interactor.FlowableUseCase
import com.tolo.app.data.model.GithubRepo
import com.tolo.app.data.repository.GithubRepository
import io.reactivex.Flowable

open class GetRepos(
    private val githubRepository: GithubRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    FlowableUseCase<List<GithubRepo>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Flowable<List<GithubRepo>> {
        return githubRepository.getRepos()
    }
}