package com.tolo.app.data.interactor

import com.tolo.app.data.executor.PostExecutionThread
import com.tolo.app.data.executor.ThreadExecutor
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers


abstract class CompletableUseCase<in Params> constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    protected abstract fun buildUseCaseObservable(params: Params? = null): Completable

    open fun execute(params: Params? = null): Completable {
        return this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
    }
}