package com.agaboardi.parchintasca.data.source.local.news

import com.agaboardi.parchintasca.news.domain.model.News
import io.reactivex.Flowable

interface NewsLocalDataContract {
    interface NewsLoadedCallback {
        fun onNewsLoaded(news: Flowable<List<News>>)
        fun onDataNotAvailable()
    }

    interface GetNewsCallback {
        fun onNewsLoaded(news: Flowable<News>)
        fun onDataNotAvailable()
    }

    fun loadNews(callback: NewsLoadedCallback)
    fun saveNews(news: List<News>)
    fun clearNews()
    fun getNews(newsId: Int, callback: GetNewsCallback)
}