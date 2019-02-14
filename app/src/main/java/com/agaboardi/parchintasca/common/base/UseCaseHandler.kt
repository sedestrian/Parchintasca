package com.agaboardi.parchintasca.common.base

object UseCaseHandler {
    private var scheduler: UseCaseScheduler? = null

    fun getInstance(): UseCaseHandler {
        scheduler = UseCaseThreadPoolScheduler()
        return this
    }

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
            useCase: UseCase<T, R>, values: T, callback: UseCase.UseCaseCallback<R>) {
        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackWrapper(callback, this)

        // The network request might be handled in a different thread so make sure
        // Espresso knows
        // that the app is busy until the response is handled.
//        EspressoIdlingResource.increment()

        scheduler?.execute(Runnable {
            useCase.run()
            // This callback may be called twice, once for the cache and once for loading
            // the data from the server API, so we check before decrementing, otherwise
            // it throws "Counter has been corrupted!" exception.
            /*if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                EspressoIdlingResource.decrement()
            }*/
        })
    }

    fun <V : UseCase.ResponseValue> notifyResponse(response: V,
                                                                                          useCaseCallback: UseCase.UseCaseCallback<V>) {
        scheduler?.notifyResponse(response, useCaseCallback)
    }

    private fun <V : UseCase.ResponseValue> notifyError(
            useCaseCallback: UseCase.UseCaseCallback<V>) {
        scheduler?.onError(useCaseCallback)
    }

    private class UiCallbackWrapper<V : UseCase.ResponseValue>(private val mCallback: UseCase.UseCaseCallback<V>,
                                                                                                      private val mUseCaseHandler: UseCaseHandler) : UseCase.UseCaseCallback<V> {

        override fun onSuccess(response: V) {
            notifyResponse(response, mCallback)
        }

        override fun onError() {
            notifyError(mCallback)
        }
    }
}