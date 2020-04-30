package com.tolo.app.data.usecase

import com.tolo.app.data.executor.PostExecutionThread
import com.tolo.app.data.executor.ThreadExecutor
import com.tolo.app.data.interactor.FlowableUseCase
import com.tolo.app.data.model.GithubPullRequest
import com.tolo.app.data.repository.PullRequestRepository
import io.reactivex.Flowable

class GetPullRequests(
    private val githubPullRequestRepository: PullRequestRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<GithubPullRequest>, GetPullRequests.Params>(
    threadExecutor,
    postExecutionThread
) {

    override fun buildUseCaseObservable(params: Params?): Flowable<List<GithubPullRequest>> {
        return githubPullRequestRepository.getPullRequest(params!!.owner, params.repo)
    }

    data class Params(val owner: String, val repo: String)
}