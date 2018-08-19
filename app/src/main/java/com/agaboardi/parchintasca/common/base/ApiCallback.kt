package com.agaboardi.parchintasca.common.base

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bipol on 3/6/2018.
 */
interface ApiCallback<T: Any>: Callback<T> {
    val DATA_FIELD get() = "data"

    override fun onFailure(call: Call<T>?, t: Throwable?)
    override fun onResponse(call: Call<T>?, response: Response<T>?)

    fun onSuccess(call: Call<T>?, response: Response<T>?)
    fun onError(call: Call<T>?, t: Throwable?)
}