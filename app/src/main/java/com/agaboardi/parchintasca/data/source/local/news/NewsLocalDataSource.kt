package com.agaboardi.parchintasca.data.source.local.news

import com.agaboardi.parchintasca.common.base.AppExecutors
import com.agaboardi.parchintasca.data.source.local.db.daos.DaoNews
import com.agaboardi.parchintasca.news.domain.model.News

class NewsLocalDataSource(private val appExecutors: AppExecutors, private val newsDao: DaoNews?): NewsLocalDataContract {
    override fun loadNews(callback: NewsLocalDataContract.NewsLoadedCallback) {
        val runnable = Runnable {
            val news = newsDao?.getNewsFlowable()

            appExecutors.mainThread().execute {
                news?.let {flowable ->
                    callback.onNewsLoaded(flowable)
                } ?: callback.onDataNotAvailable()
            }
        }

        appExecutors.diskIO().execute(runnable)
    }

    override fun saveNews(news: List<News>) {
        val saveRunnable = Runnable {
            newsDao?.saveNews(news.toMutableList())
        }
        appExecutors.diskIO().execute(saveRunnable)
    }

    override fun clearNews() {
        val clearNews = Runnable {
            newsDao?.clear()
        }
        appExecutors.diskIO().execute(clearNews)
    }

    override fun getNews(newsId: Int, callback: NewsLocalDataContract.GetNewsCallback) {
        val runnable = Runnable {
            val news = newsDao?.getNewsById(newsId)

            appExecutors.mainThread().execute {
                news?.let {
                    callback.onNewsLoaded(it)
                } ?: callback.onDataNotAvailable()
            }
        }

        appExecutors.diskIO().execute(runnable)
    }

    companion object {
        var INSTANCE: NewsLocalDataSource? = null

        fun getInstance(appExecutors: AppExecutors, newsDao: DaoNews?): NewsLocalDataSource? {
            if (INSTANCE == null) {
                synchronized(NewsLocalDataSource::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = NewsLocalDataSource(appExecutors, newsDao)
                    }
                }
            }
            return INSTANCE
        }
    }
}