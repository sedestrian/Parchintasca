package com.agaboardi.parchintasca.data.source

import com.agaboardi.parchintasca.news.domain.model.News

interface NewsDataSource {
    interface NewsLoadedCallback{
        fun onNewsLoaded(news: List<News>)
        fun onDataNotAvailable()
    }

    interface GetNewsCallback{
        fun onNewsLoaded(park: News)
        fun onDataNotAvailable()
    }

    fun loadNews(callback: NewsLoadedCallback)
    fun clearNews()
    fun getNews(newsId: Int, callback: GetNewsCallback)
}