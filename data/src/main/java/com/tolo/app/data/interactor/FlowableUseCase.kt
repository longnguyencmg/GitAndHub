package com.tolo.app.data.interactor

import com.tolo.app.data.executor.PostExecutionThread
import com.tolo.app.data.executor.ThreadExecutor
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers


abstract class FlowableUseCase<T, in Params> constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    protected abstract fun buildUseCaseObservable(params: Params? = null): Flowable<T>

    open fun execute(params: Params? = null): Flowable<T> {
        return this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
    }
}