package com.agaboardi.parchintasca.data.source

import com.agaboardi.parchintasca.data.source.local.news.NewsLocalDataContract
import com.agaboardi.parchintasca.news.domain.model.News
import io.reactivex.Flowable

class NewsRepository(
        private val localDataSource: NewsLocalDataContract?,
        private val remoteDataSource: NewsDataSource?
): NewsDataSource {
    var cachedNews: HashMap<Int, News> = hashMapOf()
    var isCacheValid: Boolean = true

    override fun loadNews(callback: NewsDataSource.NewsLoadedCallback) {
        if(cachedNews.isNotEmpty() && isCacheValid){
            callback.onNewsLoaded(cachedNews.values.toList())
            return
        }

        if(!isCacheValid){
            getNewsFromRemoteDataSource(callback)
        }else{
            localDataSource?.loadNews(object: NewsLocalDataContract.NewsLoadedCallback{
                override fun onNewsLoaded(news: Flowable<List<News>>) {
                    news.subscribe {
                        if(it.isNotEmpty()) {
                            refreshCache(it)
                            callback.onNewsLoaded(it)
                        }else{
                            getNewsFromRemoteDataSource(callback)
                        }
                    }
                }

                override fun onDataNotAvailable() {
                    getNewsFromRemoteDataSource(callback)
                }
            })
        }
    }

    override fun clearNews() {
        localDataSource?.clearNews()
        remoteDataSource?.clearNews()
    }

    override fun getNews(newsId: Int, callback: NewsDataSource.GetNewsCallback) {
        localDataSource?.getNews(newsId, object: NewsLocalDataContract.GetNewsCallback{
            override fun onNewsLoaded(news: Flowable<News>) {
                news.subscribe {
                    it?.let {
                        refreshCache(it)
                        callback.onNewsLoaded(it)
                    }
                }
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun getNewsFromRemoteDataSource(callback: NewsDataSource.NewsLoadedCallback){
        remoteDataSource?.loadNews(object: NewsDataSource.NewsLoadedCallback{
            override fun onNewsLoaded(news: List<News>) {
                refreshCache(news)
                refreshLocalDataSource(news)
                callback.onNewsLoaded(news)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    fun refreshCache(news: List<News>){
        cachedNews.clear()
        cachedNews.putAll(news.map { Pair(it.id, it) })
        isCacheValid = true
    }

    fun refreshCache(news: News){
        cachedNews.remove( news.id )
        cachedNews[news.id] = news
        isCacheValid = true
    }

    fun refreshLocalDataSource(news: List<News>){
        localDataSource?.clearNews()
        localDataSource?.saveNews(news)
        isCacheValid = true
    }
}