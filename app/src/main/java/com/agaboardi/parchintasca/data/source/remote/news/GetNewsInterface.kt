package com.agaboardi.parchintasca.data.source.remote.news

import com.agaboardi.parchintasca.news.domain.model.Records
import retrofit2.Call
import retrofit2.http.GET

interface GetNewsInterface {
    @GET("news/all/")
    fun getNews(): Call<Records>
}