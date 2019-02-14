package com.agaboardi.parchintasca.news.domain.usecase

import com.agaboardi.parchintasca.common.base.UseCase
import com.agaboardi.parchintasca.data.source.NewsDataSource
import com.agaboardi.parchintasca.data.source.NewsRepository
import com.agaboardi.parchintasca.news.domain.filter.NewsFilterFactory
import com.agaboardi.parchintasca.news.domain.filter.NewsFilterType
import com.agaboardi.parchintasca.news.domain.model.News

class GetNews(
        val newsRepository: NewsRepository,
        private val filterFactory: NewsFilterFactory
): UseCase<GetNews.RequestValues, GetNews.ResponseValue>() {
    override fun executeUseCase(requestValues: RequestValues?) {
        newsRepository.loadNews(object: NewsDataSource.NewsLoadedCallback{
            override fun onNewsLoaded(news: List<News>) {
                val currentFilter = requestValues?.currentFilter
                val query = requestValues?.currentQuery
                val newsFilter = filterFactory.create(currentFilter)

                val newsFiltered = newsFilter?.filter(news, query)
                val responseValue = ResponseValue(newsFiltered ?: listOf())
                useCaseCallback?.onSuccess(responseValue)
            }

            override fun onDataNotAvailable() {
                useCaseCallback?.onError()
            }
        })
    }

    class RequestValues(val forceUpdate: Boolean,
                        val currentFilter: NewsFilterType? = null,
                        val currentQuery: String? = null): UseCase.RequestValues
    class ResponseValue(val news: List<News>): UseCase.ResponseValue
}