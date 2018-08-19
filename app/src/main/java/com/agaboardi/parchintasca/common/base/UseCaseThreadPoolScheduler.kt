package com.agaboardi.parchintasca.common.base

import android.os.Handler
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class UseCaseThreadPoolScheduler: UseCaseScheduler {
    private val mHandler = Handler()

    private val POOL_SIZE = 32
    private val MAX_POOL_SIZE = 128
    private val TIMEOUT = 30

    private var mThreadPoolExecutor: ThreadPoolExecutor? = null

    init{
        mThreadPoolExecutor = ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT.toLong(),
                TimeUnit.SECONDS, ArrayBlockingQueue(POOL_SIZE))
    }

    override fun execute(runnable: Runnable) {
        mThreadPoolExecutor?.execute(runnable)
    }

    override fun <V : UseCase.ResponseValue> notifyResponse(response: V,
                                                                                                   useCaseCallback: UseCase.UseCaseCallback<V>) {
        mHandler.post { useCaseCallback.onSuccess(response) }
    }

    override fun <V : UseCase.ResponseValue> onError(
            useCaseCallback: UseCase.UseCaseCallback<V>) {
        mHandler.post { useCaseCallback.onError() }
    }
}