package com.tolo.app.data.executor

import io.reactivex.Scheduler


interface PostExecutionThread {
    val scheduler: Scheduler
}