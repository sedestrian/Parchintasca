package com.agaboardi.parchintasca.data.source.remote.news

import com.agaboardi.parchintasca.data.source.NewsDataSource
import com.agaboardi.parchintasca.data.source.remote.ApiManager
import com.agaboardi.parchintasca.news.domain.model.Records
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRemoteDataSource: NewsDataSource {
    override fun loadNews(callback: NewsDataSource.NewsLoadedCallback) {
        ApiManager.getNews(object: Callback<Records> {
            override fun onResponse(call: Call<Records>, response: Response<Records>) {
                if(response.isSuccessful) {
                    val news = response.body()?.records
                    news?.let {
                        callback.onNewsLoaded(it)
                    } ?: callback.onDataNotAvailable()
                }else{
                    callback.onDataNotAvailable()
                }
            }

            override fun onFailure(call: Call<Records>, t: Throwable?) {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun clearNews() {
        //Handled by repository and local data source
    }

    override fun getNews(newsId: Int, callback: NewsDataSource.GetNewsCallback) {
        callback.onDataNotAvailable()
    }

    companion object {
        private var INSTANCE: NewsRemoteDataSource? = null

        fun getInstance(): NewsRemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = NewsRemoteDataSource()
            }
            return INSTANCE!!
        }
    }
}