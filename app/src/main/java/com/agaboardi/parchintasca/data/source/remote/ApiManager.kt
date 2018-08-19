package com.agaboardi.parchintasca.data.source.remote

import com.agaboardi.parchintasca.BuildConfig
import com.agaboardi.parchintasca.common.GsonBooleanInt
import com.agaboardi.parchintasca.common.utils.LoggingInterceptor
import com.agaboardi.parchintasca.data.source.remote.main.ParksCallback
import com.agaboardi.parchintasca.data.source.remote.main.GetParksInterface
import com.agaboardi.parchintasca.data.source.remote.news.GetNewsInterface
import com.agaboardi.parchintasca.news.domain.model.Records
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Callback


/**
 * Created by Alessandro Gaboardi on 4/8/2017.
 */
object ApiManager {
    private var retrofit: Retrofit? = null

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            val httpClient = OkHttpClient.Builder().addInterceptor(LoggingInterceptor())
            retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonBooleanInt.getGson()))
                    .client(httpClient.build())
                    .build()
        }
        return retrofit!!
    }

    fun getParks(callback: ParksCallback){
        val call = getRetrofit().create(GetParksInterface::class.java)
        call.getParks().enqueue(callback)
    }

    fun getNews(callback: Callback<Records>){
        val call = getRetrofit().create(GetNewsInterface::class.java)
        call.getNews().enqueue(callback)
    }
}